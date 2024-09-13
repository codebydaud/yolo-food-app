<script setup>
import { ref, computed } from "vue";
import axios from "axios";
// import { API_CONFIG } from "../config.js";
// import { roles } from '../data/roles.js';

const baseUrl = import.meta.env.VITE_API_BASE_URL;

const title = ref("");
const description = ref("");
const interests = ref(["", "", ""]);
const restrictions = ref(["", "", ""]);

const showAIPopup = ref(false);
const userId = ref(parseInt(localStorage.getItem("user-id")));

const isTitleValid = computed(() => title.value.length <= 64);
const isDescriptionValid = computed(() => description.value.length <= 600);
const areInterestsValid = computed(() =>
  interests.value.every((i) => i.length <= 32)
);
const areRestrictionsValid = computed(() =>
  restrictions.value.every((r) => r.length <= 32)
);
const isInterestEntered = computed(() =>
  interests.value.some((i) => i.trim() !== "")
);

const successMessage = ref("");

const validateInputs = () => {
  if (!isTitleValid.value) {
    alert("Title length must be 64 characters or less.");
    return false;
  }
  if (!isDescriptionValid.value) {
    alert("Description length must be 600 characters or less.");
    return false;
  }
  if (!areInterestsValid.value) {
    alert("Each interest must be 32 characters or less.");
    return false;
  }
  if (!areRestrictionsValid.value) {
    alert("Each dietary restriction must be 32 characters or less.");
    return false;
  }
  if (!isInterestEntered.value) {
    alert("At least one interest must be entered.");
    return false;
  }
  return true;
};

const showSuccessMessage = (message) => {
  successMessage.value = message;
  setTimeout(() => {
    successMessage.value = "";
  }, 3000); // Message disappears after 3 seconds
};

const generateAIResponse = async () => {
  if (!validateInputs()) return;

  // Simulated dummy data for title and description
  const dummyIdeas = [
    {
      title: "Creamy Garlic Parmesan Pasta",
      description:
        "A rich and creamy garlic parmesan pasta that is easy to make and incredibly delicious. Perfect for a quick weeknight dinner.",
    },
    {
      title: "Spicy Thai Basil Chicken",
      description:
        "A spicy and flavorful Thai basil chicken stir-fry with fresh basil leaves, bell peppers, and a savory sauce. A quick and satisfying meal.",
    },
    {
      title: "Classic Beef Stroganoff",
      description:
        "Tender strips of beef simmered in a creamy mushroom sauce, served over egg noodles. A comforting and hearty dish that everyone will love.",
    },
  ];

  // Pick a random idea from the list
  const randomIdea = dummyIdeas[Math.floor(Math.random() * dummyIdeas.length)];

  title.value = randomIdea.title;
  description.value = randomIdea.description;

  // Show the AI popup
  showAIPopup.value = true;
};

const saveAsDraft = async () => {
  if (!validateInputs()) return;

  // Simulate saving draft with dummy data
  setTimeout(() => {
    showSuccessMessage("Draft saved successfully.");
  }, 500); // Simulate delay for demonstration
};

const submitIdea = async () => {
  if (!validateInputs()) return;

  // Simulate submitting idea with dummy data
  setTimeout(() => {
    showSuccessMessage("Idea submitted successfully.");
  }, 500); // Simulate delay for demonstration
};
</script>

<template>
  <div class="w-full mx-auto p-6">
    <h2 class="text-2xl font-bold mb-10">Generate Unique Idea</h2>

    <form @submit.prevent="submitIdea">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-12 mb-8">
        <div>
          <div class="mb-4">
            <label for="title" class="block text-gray-700 font-medium mb-3">
              Title
            </label>
            <input
              v-model="title"
              id="title"
              type="text"
              placeholder="Enter a title"
              class="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300"
              required
            />
          </div>
          <div class="flex items-center gap-6 mb-4">
            <div class="flex-1">
              <label
                for="description"
                class="block text-gray-700 font-medium mb-2"
              >
                Description
              </label>
              <textarea
                v-model="description"
                id="description"
                rows="4"
                placeholder="Enter a description"
                class="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300"
                required
              ></textarea>
            </div>
            <button
              type="button"
              @click="generateAIResponse"
              class="h-10 px-4 py-2 border border-gray-700 text-gray-700 font-medium rounded-lg shadow hover:bg-green-600 hover:text-white focus:outline-none focus:ring-2 focus:ring-green-300"
            >
              AI
            </button>
          </div>
        </div>

        <div>
          <div>
            <h3 class="text-gray-700 font-medium mb-3">Interests</h3>
            <input
              v-model="interests[0]"
              class="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300 mb-2"
              placeholder="Interest 1"
              type="text"
            />
            <input
              v-model="interests[1]"
              class="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300 mb-2"
              placeholder="Interest 2"
              type="text"
            />
            <input
              v-model="interests[2]"
              class="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300 mb-2"
              placeholder="Interest 3"
              type="text"
            />
          </div>

          <div>
            <h3 class="text-gray-700 font-medium mt-8 mb-3">
              Dietary Restrictions
            </h3>
            <input
              v-model="restrictions[0]"
              class="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300 mb-2"
              placeholder="Dietary Restriction 1"
              type="text"
            />
            <input
              v-model="restrictions[1]"
              class="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300 mb-2"
              placeholder="Dietary Restriction 2"
              type="text"
            />
            <input
              v-model="restrictions[2]"
              class="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300 mb-2"
              placeholder="Dietary Restriction 3"
              type="text"
            />
          </div>
        </div>
      </div>

      <div class="flex justify-start">
        <button
          type="button"
          @click="saveAsDraft"
          class="px-4 py-2 bg-gray-800 text-white rounded-lg shadow hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-gray-300 mr-3"
        >
          Save as Draft
        </button>

        <button
          type="submit"
          class="px-4 py-2 bg-blue-600 text-white rounded-lg shadow hover:bg-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-300"
        >
          Submit Idea
        </button>
      </div>
    </form>

    <!-- Display success message -->
    <div
      v-if="successMessage"
      class="mt-4 p-4 bg-green-100 text-green-800 border border-green-200 rounded"
    >
      {{ successMessage }}
    </div>
  </div>
</template>

<style scoped>
/* Add scoped styles if needed */
</style>
