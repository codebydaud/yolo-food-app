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
    image: new URL('@/assets/images/spaghetti.png', import.meta.url).href, 
    status: "submitted",
  },

  {
    id: 2,
    name: "Thai Chicken",
    price: "$10.99",
    servings: 1,
    image: new URL('@/assets/images/chicken.png', import.meta.url).href, 
    status: "draft",
  },

  {
    id: 3,
    name: "Classic Beef",
    price: "$19.99",
    servings: 1,
    image: new URL('@/assets/images/beef.png', import.meta.url).href, 
    status: "draft",
  },

  {
    id: 4,
    name: "Chicken Biryani",
    price: "$10.99",
    servings: 3,
    image: new URL('@/assets/images/biryani.png', import.meta.url).href, 
    status: "draft",
  },
  {
    id: 5,
    name: "Cake",
    price: "$14.50",
    servings: 5,
    image: new URL('@/assets/images/cake.png', import.meta.url).href, 
    status: "draft",
  },
  {
    id: 6,
    name: "Fish",
    price: "$10",
    servings: 1,
    image: new URL('@/assets/images/fish.png', import.meta.url).href, 
    status: "draft",
  },
  {
    id: 7,
    name: "Zinger Burger",
    price: "$12.99",
    servings: 1,
    image: new URL('@/assets/images/burger.png', import.meta.url).href, 
    status: "draft",
  },
  {
    id: 8,
    name: "Pizza",
    price: "$20",
    servings: 5,
    image: new URL('@/assets/images/pizza.png', import.meta.url).href, 
    status: "draft",
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

// Placeholder for updating a recipe
const updateRecipe = (recipe) => {
  console.log(`Updating recipe: ${recipe.name}`);
  // Add your logic for updating the recipe here
};

// Delete a recipe
const deleteRecipe = async (recipe) => {
  const confirmed = confirm(
    `Are you sure you want to delete the recipe "${recipe.name}"? This action cannot be undone.`
  );

  if (!confirmed) return;

  // Simulate deletion process with a loading state
  deletingRecipes.value[recipe.id] = true;

  setTimeout(() => {
    recipesTest.value = recipesTest.value.filter((r) => r.id !== recipe.id);
    deletingRecipes.value[recipe.id] = false;
    alert(`Recipe "${recipe.name}" has been successfully deleted.`);
  }, 1000); // Simulate a 1-second delay for deletion
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
                  @click="updateRecipe(recipe)"
                  :disabled="recipe.status === 'submitted'"
                  class="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                  :class="{
                    'cursor-not-allowed text-gray-400':
                      recipe.status === 'submitted',
                  }"
                >
                  Submit
                </button>
                <button
                  @click="viewDetails(recipe)"
                  class="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                >
                  Details
                </button>

                <button
                  @click="deleteRecipe(recipe)"
                  :disabled="recipe.status === 'submitted' || deletingRecipes[recipe.id]"
                  class="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 flex items-center"
                >
                  <!-- Show loading spinner if deleting -->
                  <span v-if="deletingRecipes[recipe.id]" class="mr-2">
                    🕓
                  </span>
                  Delete
                </button>
              </div>
            </div>

            <!-- Horizontal buttons for larger screens -->
            <div class="hidden md:flex flex-row space-x-2">
              <button
                @click="deleteRecipe(recipe)"
                :disabled="recipe.status === 'submitted' || deletingRecipes[recipe.id]"
                class="text-white text-sm px-3 py-1 rounded-md flex items-center"
                :class="{
                  'bg-red-600 hover:bg-red-700': recipe.status !== 'submitted',
                  'bg-gray-300 cursor-not-allowed':
                    recipe.status === 'submitted',
                }"
              >
                <!-- Show loading spinner if deleting -->
                <span v-if="deletingRecipes[recipe.id]" class="mr-2">
                  🕓
                </span>
                Delete
              </button>
              <button
                @click="viewDetails(recipe)"
                class="bg-blue-600 text-white text-sm px-3 py-1 rounded-md hover:bg-blue-700"
              >
                Details
              </button>
              <button
                @click="updateRecipe(recipe)"
                :disabled="recipe.status === 'submitted'"
                class="text-white text-sm px-3 py-1 rounded-md"
                :class="{
                  'bg-blue-600 hover:bg-blue-700':
                    recipe.status !== 'submitted',
                  'bg-gray-300 cursor-not-allowed':
                    recipe.status === 'submitted',
                }"
              >
                Submit
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
