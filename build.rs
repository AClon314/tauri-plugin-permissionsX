const COMMANDS: &[&str] = &["ping", "execute","test","startPersistentNotify","stopPersistentNotify"];

fn main() {
  tauri_plugin::Builder::new(COMMANDS)
    .android_path("android")
    .ios_path("ios")
    .build();
}
