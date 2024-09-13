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
  <!-- Hamburger Icon -->
  <div class="md:hidden">
    <button 
      @click="toggleMenu" 
      class="p-2 focus:outline-none focus:ring-2 focus:ring-blue-600 hamburger-button"
    >
      <!-- Hamburger Icon -->
      <i class="pi pi-bars"></i>
    </button>
  </div>

  <!-- Popup Menu for smaller screens -->
  <div v-if="isMenuOpen" class="md:hidden absolute top-16 left-4 w-48 bg-white shadow-lg rounded-lg p-4 popup-menu">
    <nav class="flex flex-col space-y-4">
      <a href="#" class="block py-2 px-4 rounded hover:bg-gray-200">Generate Idea</a>
      <a href="#" class="block py-2 px-4 rounded hover:bg-gray-200">Generated Ideas</a>
      <a href="#" class="block py-2 px-4 rounded hover:bg-gray-200">Submitted Ideas</a>
      <a href="#" class="block py-2 px-4 rounded hover:bg-gray-200">View Orders</a>
    </nav>
  </div>
</template>

