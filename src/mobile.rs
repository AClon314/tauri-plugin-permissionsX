use serde::de::DeserializeOwned;
use tauri::{
    plugin::{PluginApi, PluginHandle},
    AppHandle, Runtime,
};

use crate::models::*;

#[cfg(target_os = "android")]
const PLUGIN_IDENTIFIER: &str = "com.plugin.permissionsx";

#[cfg(target_os = "ios")]
tauri::ios_plugin_binding!(init_plugin_permissionsx);

// initializes the Kotlin or Swift plugin classes
pub fn init<R: Runtime, C: DeserializeOwned>(
    _app: &AppHandle<R>,
    api: PluginApi<R, C>,
) -> crate::Result<PermissionsX<R>> {
    #[cfg(target_os = "android")]
    let handle = api.register_android_plugin(PLUGIN_IDENTIFIER, "PermissionsxPlugin")?;
    #[cfg(target_os = "ios")]
    let handle = api.register_ios_plugin(init_plugin_permissionsx)?;
    Ok(PermissionsX(handle))
}

/// Access to the permissionsx APIs.
pub struct PermissionsX<R: Runtime>(PluginHandle<R>);

impl<R: Runtime> PermissionsX<R> {
    pub fn ping(&self, payload: PingRequest) -> crate::Result<PingResponse> {
        self.0
            .run_mobile_plugin("ping", payload)
            .map_err(Into::into)
    }
    // added command: startPersistentNotify
    pub fn startPersistentNotify(&self, payload: PersistNotifyRequest) -> crate::Result<String> {
        self.0
            .run_mobile_plugin("startPersistentNotify", payload)
            .map_err(Into::into)
    }
    pub fn stopPersistentNotify(&self) -> crate::Result<String> {
        self.0
            .run_mobile_plugin("stopPersistentNotify",())
            .map_err(Into::into)
    }
}
