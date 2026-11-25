package app.mru.ledger_mru

import android.app.Activity
import android.content.Intent
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

/**
 * Export / import of the ledger as a JSON file, through Android's own file
 * picker (Storage Access Framework). No permission needed, no plugin: the
 * user chooses where the file goes (Downloads, Drive, a Syncthing folder…).
 *
 * Channel "ledger/files":
 *  - save(name, content) → true when written, false when cancelled
 *  - open()              → file content as text, or null when cancelled
 */
class MainActivity : FlutterActivity() {
    private val channelName = "ledger/files"
    private val reqSave = 4101
    private val reqOpen = 4102

    private var pending: MethodChannel.Result? = null
    private val folder = FolderChannel(this)
    private var pendingContent: String? = null

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, FolderChannel.NAME).setMethodCallHandler(folder.handler())
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, channelName).setMethodCallHandler { call, result ->
            if (pending != null) {
                result.error("busy", "A file dialog is already open", null)
                return@setMethodCallHandler
            }
            when (call.method) {
                "save" -> {
                    pending = result
                    pendingContent = call.argument<String>("content") ?: ""
                    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "application/json"
                        putExtra(Intent.EXTRA_TITLE, call.argument<String>("name") ?: "ledger.json")
                    }
                    startActivityForResult(intent, reqSave)
                }
                "open" -> {
                    pending = result
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        // JSON files received by chat apps often come as text/plain or octet-stream.
                        type = "*/*"
                        putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/json", "text/plain", "application/octet-stream"))
                    }
                    startActivityForResult(intent, reqOpen)
                }
                else -> result.notImplemented()
            }
        }
    }

    @Deprecated("FlutterActivity is a plain Activity: the result API is the classic one")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (folder.onActivityResult(requestCode, resultCode, data)) return
        if (requestCode != reqSave && requestCode != reqOpen) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        val result = pending ?: return
        pending = null
        val uri = data?.data
        if (resultCode != Activity.RESULT_OK || uri == null) {
            result.success(if (requestCode == reqSave) false else null)
            return
        }
        try {
            if (requestCode == reqSave) {
                contentResolver.openOutputStream(uri, "wt")?.use { it.write((pendingContent ?: "").toByteArray(Charsets.UTF_8)) }
                    ?: throw IllegalStateException("Cannot write to the chosen file")
                result.success(true)
            } else {
                val text = contentResolver.openInputStream(uri)?.use { it.readBytes().toString(Charsets.UTF_8) }
                    ?: throw IllegalStateException("Cannot read the chosen file")
                result.success(text)
            }
        } catch (e: Exception) {
            result.error("io", e.message ?: e.toString(), null)
        } finally {
            pendingContent = null
        }
    }
}
