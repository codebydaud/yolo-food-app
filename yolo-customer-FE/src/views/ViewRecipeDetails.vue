<script setup>
import { ref, computed } from 'vue';
import localImage from '@/assets/images/spaghetti.png'; // Import the image

// Hardcoded recipe data for local usage
const recipe = ref({
  name: 'Garlic Pasta',
  description: 'A rich and creamy garlic parmesan pasta that is easy to make and incredibly delicious. Perfect for a quick weeknight dinner.',
  servingSize: 1,
  price: '$5',
  chefName: 'Arbaz Ahmad',
  createdAt: '2024-09-10',
  images: [localImage] // Use the imported image
});

const currentIndex = ref(0);

// Functions to handle image slideshow navigation
const nextSlide = () => {
  currentIndex.value = (currentIndex.value + 1) % recipe.value.images.length;
};

const prevSlide = () => {
  currentIndex.value = (currentIndex.value - 1 + recipe.value.images.length) % recipe.value.images.length;
};

// Computed property for current image
const currentImage = computed(() => recipe.value.images[currentIndex.value]);

// Function to handle Place Order button click
const placeOrder = () => {
  alert('Order placed for ' + recipe.value.name);
};
</script>

<template>
  <div class="flex items-center justify-center min-h-screen ">
    <div class="relative max-w-full lg:max-w-4xl w-full bg-white shadow-2xl rounded-lg overflow-hidden transition-all duration-300 hover:shadow-3xl">
      
      <!-- Image Section -->
      <div class="relative w-full h-[15rem] sm:h-[20rem] md:h-[25rem] overflow-hidden rounded-t-lg"> 
        <img :src="currentImage" alt="Recipe Image" class="image-fit rounded-t-lg transition-transform duration-300 ease-in-out transform hover:scale-105" />

        <!-- Show Buttons Only If More Than One Image -->
        <button v-if="recipe.images.length > 1" @click="prevSlide" class="absolute top-1/2 left-4 transform -translate-y-1/2 bg-gray-800 hover:bg-gray-900 text-white p-3 rounded-full shadow-md transition-colors">
          ‹
        </button>
        <button v-if="recipe.images.length > 1" @click="nextSlide" class="absolute top-1/2 right-4 transform -translate-y-1/2 bg-gray-800 hover:bg-gray-900 text-white p-3 rounded-full shadow-md transition-colors">
          ›
        </button>
      </div>

      <!-- Recipe Details -->
      <div class="p-6 sm:p-10">
        <h1 class="text-3xl sm:text-4xl font-bold text-gray-900 mb-4">{{ recipe.name }}</h1>
        <p class="text-gray-700 text-lg sm:text-xl mb-4 leading-relaxed">{{ recipe.description }}</p>
        <div class="flex flex-col md:flex-row md:justify-between md:items-center mb-6 gap-4">
          <p class="text-gray-600 text-lg"><strong>Serving Size:</strong> {{ recipe.servingSize }}</p>
          <p class="text-gray-600 text-lg"><strong>Price:</strong> {{ recipe.price }}</p>
        </div>
        <div class="bg-gray-50 p-6 rounded-lg shadow-md mb-6 space-y-2">
          <p class="text-gray-800 text-lg"><strong>Chef Name:</strong> {{ recipe.chefName }}</p>
          <p class="text-gray-800 text-lg"><strong>Created At:</strong> {{ recipe.createdAt }}</p>
        </div>

        <!-- Place Order Button -->
        <div class="flex justify-center">
          <button @click="placeOrder" class="px-4 py-2 bg-blue-600 text-white rounded-lg shadow hover:bg-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-300">
            Place Order
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.image-fit {
  object-fit: contain; 
  width: 100%;
  height: 100%;
  transition: transform 0.3s ease;
}
</style>
