package app.mru.ledger_mru

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.DocumentsContract
import io.flutter.plugin.common.MethodChannel
import java.util.concurrent.Executors

/**
 * Lasting access to one folder (e.g. the one Syncthing shares), via the
 * Storage Access Framework: the user picks it once, we keep the permission.
 *
 * Channel "ledger/folder":
 *  - pick()                       → {uri, name} or null when cancelled
 *  - list(tree)                   → [{id, name, modified}]
 *  - read(tree, id)               → text
 *  - write(tree, name, content)   → creates or overwrites the file "name"
 *  - delete(tree, id)
 *  - name(tree)                   → folder display name, or null if access was lost
 *  - release(tree)                → forget the permission
 */
class FolderChannel(private val activity: Activity) {
    companion object {
        const val NAME = "ledger/folder"
        const val REQ_PICK = 4201
    }

    private val io = Executors.newSingleThreadExecutor()
    private val main = Handler(Looper.getMainLooper())
    private var pendingPick: MethodChannel.Result? = null

    fun handler() = MethodChannel.MethodCallHandler { call, result ->
        when (call.method) {
            "pick" -> {
                if (pendingPick != null) return@MethodCallHandler result.error("busy", "Picker already open", null)
                pendingPick = result
                activity.startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE), REQ_PICK)
            }
            "list" -> background(result) { list(Uri.parse(call.argument<String>("tree"))) }
            "read" -> background(result) { read(Uri.parse(call.argument<String>("tree")), call.argument<String>("id")!!) }
            "write" -> background(result) {
                write(Uri.parse(call.argument<String>("tree")), call.argument<String>("name")!!, call.argument<String>("content")!!)
                true
            }
            "delete" -> background(result) {
                val tree = Uri.parse(call.argument<String>("tree"))
                DocumentsContract.deleteDocument(activity.contentResolver, DocumentsContract.buildDocumentUriUsingTree(tree, call.argument<String>("id")!!))
            }
            "name" -> background(result) { folderName(Uri.parse(call.argument<String>("tree"))) }
            "release" -> background(result) {
                val tree = Uri.parse(call.argument<String>("tree"))
                runCatching {
                    activity.contentResolver.releasePersistableUriPermission(
                        tree, Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                }
                true
            }
            else -> result.notImplemented()
        }
    }

    /** Called from MainActivity.onActivityResult. Returns true when handled. */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (requestCode != REQ_PICK) return false
        val result = pendingPick ?: return true
        pendingPick = null
        val uri = data?.data
        if (resultCode != Activity.RESULT_OK || uri == null) {
            result.success(null)
            return true
        }
        try {
            activity.contentResolver.takePersistableUriPermission(
                uri, Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            result.success(mapOf("uri" to uri.toString(), "name" to (folderName(uri) ?: "folder")))
        } catch (e: Exception) {
            result.error("io", e.message ?: e.toString(), null)
        }
        return true
    }

    private fun <T> background(result: MethodChannel.Result, work: () -> T) {
        io.execute {
            try {
                val v = work()
                main.post { result.success(v) }
            } catch (e: Exception) {
                main.post { result.error("io", e.message ?: e.toString(), null) }
            }
        }
    }

    private fun hasAccess(tree: Uri) =
        activity.contentResolver.persistedUriPermissions.any { it.uri == tree && it.isReadPermission && it.isWritePermission }

    private fun folderName(tree: Uri): String? {
        if (!hasAccess(tree)) return null
        val doc = DocumentsContract.buildDocumentUriUsingTree(tree, DocumentsContract.getTreeDocumentId(tree))
        activity.contentResolver.query(doc, arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME), null, null, null)?.use {
            if (it.moveToFirst()) return it.getString(0)
        }
        return null
    }

    private fun list(tree: Uri): List<Map<String, Any?>> {
        val children = DocumentsContract.buildChildDocumentsUriUsingTree(tree, DocumentsContract.getTreeDocumentId(tree))
        val cols = arrayOf(
            DocumentsContract.Document.COLUMN_DOCUMENT_ID,
            DocumentsContract.Document.COLUMN_DISPLAY_NAME,
            DocumentsContract.Document.COLUMN_LAST_MODIFIED,
            DocumentsContract.Document.COLUMN_MIME_TYPE,
        )
        val out = mutableListOf<Map<String, Any?>>()
        activity.contentResolver.query(children, cols, null, null, null)?.use { c ->
            while (c.moveToNext()) {
                if (c.getString(3) == DocumentsContract.Document.MIME_TYPE_DIR) continue
                out += mapOf("id" to c.getString(0), "name" to c.getString(1), "modified" to c.getLong(2))
            }
        }
        return out
    }

    private fun read(tree: Uri, id: String): String {
        val doc = DocumentsContract.buildDocumentUriUsingTree(tree, id)
        return activity.contentResolver.openInputStream(doc)?.use { it.readBytes().toString(Charsets.UTF_8) }
            ?: throw IllegalStateException("Cannot read $id")
    }

    private fun write(tree: Uri, name: String, content: String) {
        val existing = list(tree).firstOrNull { it["name"] == name }?.get("id") as String?
        val doc = if (existing != null) {
            DocumentsContract.buildDocumentUriUsingTree(tree, existing)
        } else {
            val parent = DocumentsContract.buildDocumentUriUsingTree(tree, DocumentsContract.getTreeDocumentId(tree))
            DocumentsContract.createDocument(activity.contentResolver, parent, "application/json", name)
                ?: throw IllegalStateException("Cannot create $name")
        }
        activity.contentResolver.openOutputStream(doc, "wt")?.use { it.write(content.toByteArray(Charsets.UTF_8)) }
            ?: throw IllegalStateException("Cannot write $name")
    }
}
