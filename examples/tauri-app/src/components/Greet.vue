<script setup lang="ts">
import { ref } from "vue";
import { invoke } from "@tauri-apps/api/core";

const greetMsg = ref("");
const name = ref("");

async function greet() {
  // Learn more about Tauri commands at https://tauri.app/v1/guides/features/command
  greetMsg.value = await invoke("greet", { name: name.value });
  console.log(await invoke.length);
  console.log(await invoke('plugin:permissionsx|test',{msg: "yep"}));

  // const state = await invoke<Permissions>('plugin:permissionsx|requestPermissions', { permissions: ['readExtStorage'] });
  // console.log(state);
}

async function start(){
  console.log(await invoke('plugin:permissionsx|startPersistentNotify',{title: "测试2",content: name.value}));
}
async function stop(){
  console.log(await invoke('plugin:permissionsx|stopPersistentNotify'));
}
</script>

<template>
  <button @click="start">start</button>
  <button @click="stop">stop</button>
  <form class="row" @submit.prevent="greet">
    <input id="greet-input" v-model="name" placeholder="Enter a name..." />
    <button type="submit">Greet</button>
  </form>

  <p>{{ greetMsg }}</p>
</template>
