<script setup>
import { useRoute } from 'vue-router'; 


const route = useRoute();


const isActiveLink = (link) => {
  return route.path === link;
};
</script>

<template>
  <nav class="hidden md:block w-64 bg-blue-600 text-white flex-col p-4 space-y-4 rounded-tr-2xl">
    <h1 class="block py-2 px-4 text-3xl font-bold text-white mb-8">YOLO</h1>

    <!-- Router Links -->
    <router-link
      to="/"
      class="block py-2 px-4 font-semibold rounded"
      :class="isActiveLink('/') ? 'bg-white text-black' : 'hover:bg-blue-500 text-white'"
    >
      Home
    </router-link>

    <router-link
      to="/generate-idea"
      class="block py-2 px-4 font-semibold rounded"
      :class="isActiveLink('/generate-idea') ? 'bg-white text-black' : 'hover:bg-blue-500 text-white'"
    >
      Generate Idea
    </router-link>

    <router-link
      to="/view-ideas"
      class="block py-2 px-4 font-semibold rounded"
      :class="isActiveLink('/view-ideas') ? 'bg-white text-black' : 'hover:bg-blue-500 text-white'"
    >
      View Ideas
    </router-link>

    <router-link
      to="/view-orders"
      class="block py-2 px-4 font-semibold rounded"
      :class="isActiveLink('/view-orders') ? 'bg-white text-black' : 'hover:bg-blue-500 text-white'"
    >
      View Orders
    </router-link>
  </nav>
</template>
