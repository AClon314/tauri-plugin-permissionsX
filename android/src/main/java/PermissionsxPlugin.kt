package com.plugin.permissionsx

import android.app.Activity
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import app.tauri.plugin.Invoke

@InvokeArg
class PingArgs {
  var value: String? = null
}

// enum class PermissionState(val value: String) {
//   GRANTED("granted"),
//   DENIED("denied"),
//   PROMPT("prompt"),
//   PROMPT_WITH_RATIONALE("prompt-with-rationale")
// }

// @InvokeArg
// class PermissionsXArgs {
//   // init value 'prompt'
//   var permissionx: PermissionState = PermissionState.PROMPT
// }

@TauriPlugin(
  permissions = [
    Permission(strings = [Manifest.permission.READ_EXTERNAL_STORAGE], alias = "readExtStorage")
  ]
)
class PermissionsxPlugin(private val activity: Activity): Plugin(activity) {
    private val implementation = Example()

    @Command
    fun ping(invoke: Invoke) {
        val args = invoke.parseArgs(PingArgs::class.java)

        val ret = JSObject()
        ret.put("value", implementation.pong(args.value ?: "default value :("))
        invoke.resolve(ret)
    }

    // @Command
    // fun checkPermissionsX(invoke: Invoke) {
    //     val args = invoke.parseArgs(PermissionsXArgs::class.java)

    //     val ret = JSObject()
    //     ret.put("value", implementation.checkPermissionsX(args.value ?: "default value :("))
    //     invoke.resolve(ret)
    // }
}
