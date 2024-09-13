<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
const isMenuOpen = ref(false);


const toggleMenu = () => {
  isMenuOpen.value = !isMenuOpen.value;
};


const closeMenu = () => {
  isMenuOpen.value = false;
};


const handleClickOutside = (event) => {
  const menu = document.querySelector('.popup-menu'); 
  const hamburgerButton = document.querySelector('.hamburger-button'); 
  if (menu && !menu.contains(event.target) && !hamburgerButton.contains(event.target)) {
    closeMenu(); 
  }
};


onMounted(() => {
  document.addEventListener('click', handleClickOutside);
});


onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside);
});
</script>

<template>
  <div class="md:hidden">
    <button 
      @click="toggleMenu" 
      class="p-2 focus:outline-none focus:ring-2 focus:ring-blue-600 hamburger-button"
    >

      <svg class="w-8 h-8 text-gray-700" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
        <path stroke-linecap="round" stroke-linejoin="round" d="M4 6h16M4 12h16m-7 6h7" />
      </svg>
    </button>
  </div>

  <div v-if="isMenuOpen" class="md:hidden absolute top-16 left-4 w-48 bg-white shadow-lg rounded-lg p-4 popup-menu">
    <nav class="flex flex-col space-y-4">
      <a href="/" class="block py-2 px-4 rounded hover:bg-gray-200">Home</a>
      <a href="/ideas" class="block py-2 px-4 rounded hover:bg-gray-200">Ideas</a>
      <a href="/recipies" class="block py-2 px-4 rounded hover:bg-gray-200">Recipies</a>
      <a href="/orders" class="block py-2 px-4 rounded hover:bg-gray-200">Orders</a>
    </nav>
  </div>
</template>