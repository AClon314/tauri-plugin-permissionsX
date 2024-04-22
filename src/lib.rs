use tauri::{
    plugin::{Builder, TauriPlugin},
    Manager, Runtime,
};

use std::{collections::HashMap, sync::Mutex};

pub use models::*;

#[cfg(desktop)]
mod desktop;
#[cfg(mobile)]
mod mobile;

mod commands;
mod error;
mod models;

pub use error::{Error, Result};

#[cfg(desktop)]
use desktop::PermissionsX;
#[cfg(mobile)]
use mobile::PermissionsX;

#[derive(Default)]
struct MyState(Mutex<HashMap<String, String>>);

/// Extensions to [`tauri::App`], [`tauri::AppHandle`] and [`tauri::Window`] to access the permissions APIs.
pub trait PermissionsXExt<R: Runtime> {
    fn permissionsx(&self) -> &PermissionsX<R>;
}

impl<R: Runtime, T: Manager<R>> crate::PermissionsXExt<R> for T {
    fn permissionsx(&self) -> &PermissionsX<R> {
        self.state::<PermissionsX<R>>().inner()
    }
}

/// Initializes the plugin.
pub fn init<R: Runtime>() -> TauriPlugin<R> {
    Builder::new("permissionsx")
        .invoke_handler(tauri::generate_handler![
            commands::test,
            // commands::startPersistentNotify,
            // commands::stopPersistentNotify,
        ])
        .setup(|app, api| {
            #[cfg(mobile)]
            let permissionsx = mobile::init(app, api)?;
            #[cfg(desktop)]
            let permissionsx = desktop::init(app, api)?;
            app.manage(permissionsx);

            // manage state so it is accessible by the commands
            app.manage(MyState::default());
            Ok(())
        })
        .build()
}
