use serde::de::DeserializeOwned;
use tauri::{plugin::PluginApi, AppHandle, Runtime};

use crate::models::*;

pub fn init<R: Runtime, C: DeserializeOwned>(
    app: &AppHandle<R>,
    _api: PluginApi<R, C>,
) -> crate::Result<PermissionsX<R>> {
    Ok(PermissionsX(app.clone()))
}

/// Access to the PermissionsX APIs.
pub struct PermissionsX<R: Runtime>(AppHandle<R>);

impl<R: Runtime> PermissionsX<R> {
    pub fn ping(&self, payload: PingRequest) -> crate::Result<PingResponse> {
        Ok(PingResponse {
            value: payload.value,
        })
    }
    // added command: startPersistentNotify
    pub fn start_persistent_notify(&self, payload: PersistNotifyRequest) -> crate::Result<String> {
        Ok("⚠warning:start_persistent_notify() not implemented on desktop".to_string())
    }
    pub fn stop_persistent_notify(&self) -> crate::Result<String> {
        Ok("⚠warning:start_persistent_notify() not implemented on desktop".to_string())
    }
}
