<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';


const recipe = ref({
  name: '',
  description: '',
  servingSize: 0,
  price: '',
  ideaTitle: '',
  status: '',
  createdAt: '',
  images: []
});


const currentIndex = ref(0);
const loading = ref(true);
const errorMessage = ref('');

const route = useRoute();
const recipeId = route.params.recipeId; 


onMounted(async () => {
  try {
    const response = await axios.get(`http://localhost:8082/api/v1/recipes/${recipeId}`, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('vue-token')}`
      }
    });

    recipe.value = {
      name: response.data.recipe.recipe_name,
      description: response.data.recipe.description,
      servingSize: response.data.recipe.serving_size,
      price: `$${(response.data.recipe.price / 100).toFixed(2)}`, 
      ideaTitle: response.data.recipe.idea_title,
      status: response.data.recipe.status,
      createdAt: response.data.recipe.created_at,
      images: response.data.recipe.images.map(img => `http://localhost:8085/${img.url.match(/[^\\/]+$/)[0]}`)
    };
  } catch (error) {
    console.error('An error occurred:', error);
    errorMessage.value = 'Failed to load recipe details. Please try again later.';
  } finally {
    loading.value = false;
  }
});


const nextSlide = () => {
  currentIndex.value = (currentIndex.value + 1) % recipe.value.images.length;
};

const prevSlide = () => {
  currentIndex.value = (currentIndex.value - 1 + recipe.value.images.length) % recipe.value.images.length;
};


const currentImage = computed(() => recipe.value.images[currentIndex.value]);
</script>

<template>
  <div class="flex items-center justify-center min-h-screen bg-gray-50 p-6">
    <div class="relative max-w-full lg:max-w-7xl w-full bg-white shadow-lg rounded-lg overflow-hidden">

      <div class="relative w-full h-[32rem] overflow-hidden"> 
        <img :src="currentImage" alt="Recipe Image" class="image-fit" />

        <button @click="prevSlide" class="absolute top-1/2 left-4 transform -translate-y-1/2 bg-gray-700 text-white p-3 rounded-full shadow-md">
          ‹
        </button>
        <button @click="nextSlide" class="absolute top-1/2 right-4 transform -translate-y-1/2 bg-gray-700 text-white p-3 rounded-full shadow-md">
          ›
        </button>
      </div>

      <div class="p-10">
        <h1 class="text-4xl sm:text-5xl font-extrabold mb-6 text-gray-900">{{ recipe.name }}</h1>
        <p class="text-gray-700 text-lg sm:text-xl mb-6">{{ recipe.description }}</p>
        <div class="flex flex-col md:flex-row md:justify-between md:items-center mb-6">
          <p class="text-gray-600 text-lg"><strong>Serving Size:</strong> {{ recipe.servingSize }}</p>
          <p class="text-gray-600 text-lg"><strong>Price:</strong> {{ recipe.price }}</p>
        </div>
        <div class="bg-gray-100 p-8 rounded-lg shadow-md mb-6">
          <p class="text-gray-800 text-lg mb-2"><strong>Idea Title:</strong> {{ recipe.ideaTitle }}</p>
          <p class="text-gray-800 text-lg mb-2"><strong>Status:</strong> {{ recipe.status }}</p>
          <p class="text-gray-800 text-lg"><strong>Created At:</strong> {{ recipe.createdAt }}</p>
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
}
</style>
