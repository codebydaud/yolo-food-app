<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

// Initialize router
const router = useRouter();

// Dummy recipes data
const recipesTest = ref([
  {
    id: 1,
    name: "Garlic Pasta",
    price: "$5",
    servings: 1,
    image: new URL("@/assets/images/spaghetti.png", import.meta.url).href,
    status: "submitted",
  },
]);

// Simulate loading state
const loading = ref(true);
const errorMessage = ref("");

// Optional: Track deletion loading state per recipe
const deletingRecipes = ref({});

onMounted(() => {
  // Simulate an API delay before showing the dummy data
  setTimeout(() => {
    loading.value = false;
  }, 1000); // 1 second delay for simulating API response
});

// Navigate to recipe details
const viewDetails = (recipe) => {
  router
    .push({
      name: "ViewRecipeDetails",
      params: { recipeId: recipe.id },
    })
    .catch((err) => {
      console.error("Navigation error:", err);
    });
};

// State to track the visibility of the dropdown
const dropdownVisible = ref({});

// Toggle dropdown visibility for a specific recipe
const toggleDropdown = (recipeId) => {
  dropdownVisible.value[recipeId] = !dropdownVisible.value[recipeId];
};
</script>

<template>
  <div class="container mx-auto p-4">
    <!-- Loading State -->
    <div v-if="loading" class="text-center text-xl">Loading...</div>

    <!-- Error Message -->
    <div v-else-if="errorMessage" class="text-center text-red-500">
      {{ errorMessage }}
    </div>

    <!-- Recipes List -->
    <div v-else class="grid grid-cols-1 gap-4">
      <div
        v-for="recipe in recipesTest"
        :key="recipe.id"
        class="flex flex-col md:flex-row items-start p-4 border rounded-md shadow-sm"
      >
        <div class="flex items-start w-full">
          <!-- Recipe Image -->
          <img
            :src="recipe.image"
            alt="Recipe Thumbnail"
            class="w-14 h-14 md:w-20 md:h-20 rounded-full object-cover"
          />

          <!-- Recipe Details -->
          <div class="flex-1 ml-4">
            <span class="text-sm font-semibold md:text-xl">
              {{ recipe.name }}
            </span>
            <div class="text-gray-600 text-sm md:text-base mt-1">
              <p>Price: {{ recipe.price }}</p>
              <p>Servings: {{ recipe.servings }}</p>
            </div>
          </div>

          <!-- Actions -->
          <div class="ml-auto mt-3 md:mt-0">
            <!-- Dropdown for smaller screens -->
            <div class="block md:hidden relative">
              <button
                @click="toggleDropdown(recipe.id)"
                class="bg-blue-200 text-gray-700 px-4 py-2 rounded-md focus:outline-none flex items-center"
              >
                Actions
                <span class="ml-2">&#9662;</span>
                <!-- Down arrow -->
              </button>
              <div
                v-if="dropdownVisible[recipe.id]"
                class="absolute right-0 mt-2 w-48 bg-white border rounded-md shadow-lg z-10"
              >
                <button
                  @click="viewDetails(recipe)"
                  class="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                >
                  Details
                </button>
              </div>
            </div>

            <!-- Horizontal buttons for larger screens -->
            <div class="hidden md:flex flex-row space-x-2">
              <button
                @click="viewDetails(recipe)"
                class="bg-blue-600 text-white text-sm px-3 py-1 rounded-md hover:bg-blue-700"
              >
                Details
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.ml-auto {
  margin-left: auto;
}
.mt-1 {
  margin-top: 0.25rem;
}
.md\:mt-0 {
  margin-top: 0;
}
.mt-3 {
  margin-top: 0.75rem;
}
</style>
