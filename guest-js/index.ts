import { invoke } from "@tauri-apps/api/core";

export async function execute() {
  await invoke("plugin:permissionsx|execute");
}

export async function startPersistentNotify(title?: string, content?: string) {
  return await invoke("plugin:permissionsx|startPersistentNotify", {
    title: title,
    content: content,
  });
}

export async function stopPersistentNotify() {
  return await invoke("plugin:permissionsx|stopPersistentNotify");
}
