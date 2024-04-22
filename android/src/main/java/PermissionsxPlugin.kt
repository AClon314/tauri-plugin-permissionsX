package com.plugin.permissionsx

import android.app.Activity
import android.content.Intent
import android.util.Log
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.Invoke
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import com.plugin.permissionsx.MyForegroundService

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
// internal class PermissionsXArgs {
//   // init value 'prompt'
//   var permissionx: PermissionState = PermissionState.PROMPT
// }

@InvokeArg
internal class ForegroundServiceArgs {
  var title: String? = null
  var content: String? = null
}

@TauriPlugin
class PermissionsxPlugin(private val activity: Activity) : Plugin(activity) {
  override fun onNewIntent(intent: Intent) {
    Log.d("PermissionsxPlugin", "onNewIntent")
  }

  private val myForegroundService = MyForegroundService()
  private val implementation = Example()

  @Command
  fun ping(invoke: Invoke) {
    val args = invoke.parseArgs(PingArgs::class.java)

    val ret = JSObject()
    ret.put("value", implementation.pong(args.value ?: "default value :("))
    invoke.resolve(ret)
  }

  @Command
  fun startPersistentNotify(invoke: Invoke) {
    val args = invoke.parseArgs(ForegroundServiceArgs::class.java)
    val ret = JSObject()
    ret.put("title", args.title)
    ret.put("content", args.content)
    invoke.resolve(ret)

    myForegroundService.startService(args.title, args.content)
  }

  @Command
  fun stopPersistentNotify(invoke: Invoke) {
    val ret = JSObject()
    val ans = myForegroundService.stopService()
    ret.put("stopPersistentNotify", ans)
    invoke.resolve(ret)
  }
}
