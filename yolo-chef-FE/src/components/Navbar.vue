<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import Hamburger from '@/components/HamburgerMenu.vue'; 


const dropdownOpen = ref(false);


const toggleDropdown = () => {
  dropdownOpen.value = !dropdownOpen.value;
};


const handleClickOutside = (event) => {
  const menu = document.querySelector('.popup-menu'); 
  const hamburgerButton = document.querySelector('.user-button'); 
  if (menu && !menu.contains(event.target) && !hamburgerButton.contains(event.target)) {
    closeMenu(); 
  }
};

const closeMenu = () => {
    dropdownOpen.value = false;
};


onMounted(() => {
  document.addEventListener('click', handleClickOutside);
});


onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside);
});

</script>

<template>
  <div class="flex items-center justify-between h-16 px-4 bg-white border-b md:border-none shadow-md md:shadow-none">
    
    <Hamburger class="md:hidden" /> 

    <div class="flex items-center">
      <i class="pi pi-utensils text-green-500 text-2xl mr-2"></i> 
      <h1 class="block md:hidden text-2xl font-bold text-gray-800">YOLO</h1>
      <h1 class="hidden md:block text-2xl font-bold text-blue-600 ml-2">On-demand Food Ordering Platform </h1>

    </div>

    <div class="flex items-center space-x-4">
   
      <button class="relative" @click="toggleDropdown">
        <i class="pi pi-user text-gray-600 text-xl md:text-2xl user-button"></i>
        <div v-if="dropdownOpen" class="absolute right-0 mt-2 w-48 bg-white border rounded shadow-md popup-menu">
          <a href="#" class="block px-4 py-2 text-gray-700 hover:bg-gray-100">Profile</a>
          <a href="#" @click="$logout" class="block px-4 py-2 text-gray-700 hover:bg-gray-100">Logout</a>
        </div>
      </button>
    </div>
  </div>
</template>