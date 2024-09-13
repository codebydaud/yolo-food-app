<template>
  <div class="container mx-auto p-6">
    <div v-if="loading" class="text-center text-gray-500">Loading...</div>
    <div v-else>
     
      
      <div class="mb-6 flex justify-end">
        <input 
          v-model="filterTitle" 
          type="text" 
          placeholder="Filter by title..." 
          class="border border-gray-300 rounded-lg px-4 py-2 w-64"
          @change="getIdeasWithFilter(filterTitle)"
        />
      </div>

      <div>
        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-4 bg-gray-100 text-gray-700 font-semibold mb-4 rounded-lg shadow-md">
          <div class="p-3 border-b border-gray-300">Customer Name</div>
          <div class="p-3 border-b border-gray-300">Title</div>
          <div class="p-3 border-b border-gray-300">Created At</div>
          <div class="p-3 border-b border-gray-300">Actions</div>
        </div>
        <div v-for="idea in ideas" :key="idea.id" class="bg-white rounded-lg shadow-lg mb-4">
          <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-4 p-4 border-b border-gray-200">
            <div class="text-gray-800">{{ idea.customerName }}</div>
            <div class="text-gray-800">{{ idea.title }}</div>
            <div class="text-gray-500">{{ formatDate(idea.createdAt) }}</div>
            <div class="flex justify-center md:justify-start">
              <button 
                @click="viewIdea(idea.id)" 
                class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition duration-150">
                View Details
              </button>
            </div>
          </div>
        </div>
         <div v-if="errorMessage" class="mb-6 text-center text-red-500">{{ errorMessage }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();
const ideas = ref([]);
const loading = ref(true);
const errorMessage = ref('');
const storedToken = localStorage.getItem("vue-token");

onMounted(async () => {
  if (storedToken) {
    try {
      const response = await axios.get('http://localhost:8082/api/v1/ideas', {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${storedToken}`
        }
      });
      ideas.value = response.data.ideas;
    } catch (error) {
      handleError(error);
    } finally {
      loading.value = false;
    }
  } else {
    errorMessage.value = 'No token found. Please log in.';
    loading.value = false;
  }
});

function formatDate(dateString) {
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  return new Date(dateString).toLocaleDateString(undefined, options);
}

function viewIdea(id) {
 
  router.push({
    name: 'GenerateRecipe',
    params: { id }
  }).catch(err => {
    console.error('Navigation error:', err);
  });
}

const filterTitle = ref('');

async function getIdeasWithFilter(title) {
  try {
    // Clear the previous results
    ideas.value = [];

    const response = await axios.get('http://localhost:8082/api/v1/ideas', {
      params: { title },
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${storedToken}`
      }
    });

    // Check if the response contains any ideas
    if (response.data.ideas && response.data.ideas.length > 0) {
      ideas.value = response.data.ideas;
    } else {
      errorMessage.value = 'No ideas found matching the title.';
    }

  } catch (error) {
    handleError(error);
  }
}
function handleError(error) {
  if (error.response) {
    switch (error.response.status) {
      case 400:
        errorMessage.value = 'Bad Request: ' + error.response.data.message;
        break;
      case 404:
        errorMessage.value = 'The resource you requested could Not Found.';
        break;
      case 500:
        errorMessage.value = 'Server Error: An internal server error occurred. Please try again later.';
        break;
      default:
        errorMessage.value = 'An error occurred: ' + error.response.data.message;
    }
  } else if (error.request) {
    errorMessage.value = 'No response received from server. Please check your network connection.';
  } else {
    errorMessage.value = 'An unexpected error occurred: ' + error.message;
  }
}
</script>
