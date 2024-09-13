<script setup>
import { ref, computed } from "vue";

// Form fields
const name = ref("");
const description = ref("");
const servingSize = ref("");
const price = ref("");

// Image upload
const selectedImage = ref(null);
const imageUrl = ref(null);

// State for showing success message
const successMessage = ref("");

// Field validations
const isNameValid = computed(() => name.value.length <= 64);
const isDescriptionValid = computed(() => description.value.length <= 600);
const isServingSizeValid = computed(() => servingSize.value.length <= 32);
const isPriceValid = computed(() => price.value.length <= 32);

// Input validation function
const validateInputs = () => {
  if (!isNameValid.value) {
    alert("Name length must be 64 characters or less.");
    return false;
  }
  if (!isDescriptionValid.value) {
    alert("Description length must be 600 characters or less.");
    return false;
  }
  if (!isServingSizeValid.value) {
    alert("Serving size length must be 32 characters or less.");
    return false;
  }
  if (!isPriceValid.value) {
    alert("Price length must be 32 characters or less.");
    return false;
  }
  return true;
};

// Handle image upload
const onImageSelected = (event) => {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = () => {
      imageUrl.value = reader.result;
    };
    reader.readAsDataURL(file);
    selectedImage.value = file;
  }
};

// Show success message
const showSuccessMessage = (message) => {
  successMessage.value = message;
  setTimeout(() => {
    successMessage.value = "";
  }, 3000); // Message disappears after 3 seconds
};

const generateAIResponse = async () => {
  if (!validateInputs()) return;

  // Simulated dummy data for recipes
  const dummyRecipes = [
    {
      name: "Garlic Pasta",
      description:
        "A rich and creamy garlic parmesan pasta that is easy to make and incredibly delicious. Perfect for a quick weeknight dinner.",
      servingSize: "1",
      price: "$5",
    },
  ];

  // Pick a random recipe from the list
  const randomRecipe =
    dummyRecipes[Math.floor(Math.random() * dummyRecipes.length)];

  // Fill the form with the AI-generated data
  name.value = randomRecipe.name;
  description.value = randomRecipe.description;
  servingSize.value = randomRecipe.servingSize;
  price.value = randomRecipe.price;
};

const saveAsDraft = async () => {
  if (!validateInputs()) return;
  showSuccessMessage("Draft saved successfully.");
};

const submitIdea = async () => {
  if (!validateInputs()) return;
  showSuccessMessage("Recipe submitted successfully.");
};
</script>

<template>
  <div class="w-full mx-auto p-6">
    <h2 class="text-2xl font-bold mb-10">Generate Unique Recipe</h2>

    <div class="grid grid-cols-1 lg:grid-cols-2 gap-12">
      <!-- Form Section -->
      <form @submit.prevent="submitIdea">
        <div class="mb-8">
          <div class="mb-4">
            <label for="name" class="block text-gray-700 font-medium mb-3"
              >Name</label
            >
            <input
              v-model="name"
              id="name"
              type="text"
              placeholder="Enter a name"
              class="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300"
              required
            />
          </div>
          <div class="mb-4">
            <label
              for="description"
              class="block text-gray-700 font-medium mb-2"
              >Description</label
            >
            <textarea
              v-model="description"
              id="description"
              rows="4"
              placeholder="Enter a description"
              class="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300"
              required
            ></textarea>
          </div>
          <div class="mb-4">
            <label
              for="servingSize"
              class="block text-gray-700 font-medium mb-2"
              >Serving Size</label
            >
            <input
              v-model="servingSize"
              id="servingSize"
              type="text"
              placeholder="Enter serving size"
              class="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300"
              required
            />
          </div>
          <div class="mb-4">
            <label for="price" class="block text-gray-700 font-medium mb-2"
              >Price</label
            >
            <input
              v-model="price"
              id="price"
              type="text"
              placeholder="Enter price"
              class="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300"
              required
            />
          </div>
          <div class="flex items-center gap-6 mb-4">
            <button
              type="button"
              @click="generateAIResponse"
              class="h-10 px-4 py-2 border border-gray-700 text-gray-700 font-medium rounded-lg shadow hover:bg-green-600 hover:text-white focus:outline-none focus:ring-2 focus:ring-green-300"
            >
              AI
            </button>
          </div>
        </div>

        <div class="flex justify-end gap-4">
          <button
            type="button"
            @click="saveAsDraft"
            class="px-4 py-2 bg-gray-800 text-white rounded-lg shadow hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-gray-300"
          >
            Save as Draft
          </button>
          <button
            type="submit"
            class="px-4 py-2 bg-blue-600 text-white rounded-lg shadow hover:bg-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-300"
          >
            Submit Recipe
          </button>
        </div>
      </form>

      <!-- Image Upload Section -->
      <div>
        <h3 class="text-xl font-bold mb-4">Upload an Image</h3>
        <input type="file" @change="onImageSelected" accept="image/*" />

        <!-- Image Preview -->
        <div v-if="imageUrl" class="mt-4">
          <img
            :src="imageUrl"
            alt="Preview"
            class="max-w-full max-h-96 object-contain border border-gray-300 p-2 rounded-md shadow-md"
          />
        </div>
      </div>
    </div>

    <!-- Success Message -->
    <transition name="fade" @after-leave="successMessage = ''">
      <div
        v-if="successMessage"
        class="fixed top-4 right-4 bg-green-500 text-white p-4 rounded-lg shadow-lg"
      >
        {{ successMessage }}
      </div>
    </transition>
  </div>
</template>

<style scoped>
/* Optional fade transition */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
