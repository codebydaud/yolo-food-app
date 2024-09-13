  <script setup>
  
  import Sidebar from '@/components/Sidebar.vue';
  import Navbar from '@/components/Navbar.vue';
  </script>
  
<template>
    <div class="flex h-screen">
      <Sidebar />
      <div class="flex flex-1 flex-col">
        <Navbar />
        <main class="flex-1 p-4">
          <RouterView />
        </main>
      </div>
    </div>
  </template>
  
