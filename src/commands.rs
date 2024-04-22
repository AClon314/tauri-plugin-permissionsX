use tauri::{command, AppHandle, Runtime, State, Window};

use crate::{PermissionsX, Result};

// #[command]
// pub(crate) async fn execute<R: Runtime>(
//   _app: AppHandle<R>,
//   _window: Window<R>,
//   state: State<'_, MyState>,
// ) -> Result<String> {
//   state.0.lock().unwrap().insert("key".into(), "value".into());
//   Ok("success".to_string())
// }

#[command]
pub(crate) async fn test<R: Runtime>(
    _app: AppHandle<R>,
    _window: Window<R>,
    msg: String,
) -> Result<String> {
    Ok(msg)
}

#[command]
pub(crate) async fn startPersistentNotify<R: Runtime>(
    _app: AppHandle<R>,
    _window: Window<R>,
    persist_notify: State<'_, PermissionsX<R>>,
    // options: crate::PersistNotifyRequest,
    title: Option<String>,
    content: Option<String>,
) -> Result<String> {
    persist_notify.start_persistent_notify(crate::PersistNotifyRequest {title:title, content:content})
}

#[command]
pub(crate) async fn stopPersistentNotify<R: Runtime>(
    _app: AppHandle<R>,
    persist_notify: State<'_, PermissionsX<R>>,
) -> Result<String> {
    persist_notify.stop_persistent_notify()
}
