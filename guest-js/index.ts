import { invoke } from "@tauri-apps/api/core";

export async function execute() {
  await invoke("plugin:permissionsx|execute");
}

export async function test(msg?: string) {
  return await invoke("plugin:permissionsx|test", { msg: msg });
}

export async function startPersistentNotify(title?: string, content?: string): Promise<void> {
  return await invoke("plugin:permissionsx|startPersistentNotify", {
    title: title,
    content: content,
  });
}

export async function stopPersistentNotify(): Promise<boolean> {
  return await invoke("plugin:permissionsx|stopPersistentNotify");
}
