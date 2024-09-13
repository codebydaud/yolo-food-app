<script setup>
import { ref, computed } from "vue";
import axios from "axios";
import { API_CONFIG } from "../config.js";
import { roles } from '../data/roles.js'; 

const baseUrl = import.meta.env.VITE_API_BASE_URL

const title = ref("");
const description = ref("");
const interests = ref(["", "", ""]);
const restrictions = ref(["", "", ""]);

const showAIPopup = ref(false);
const userId = ref(parseInt(localStorage.getItem("user-id")));

const isTitleValid = computed(() => title.value.length <= 64);
const isDescriptionValid = computed(() => description.value.length <= 600);
const areInterestsValid = computed(() => interests.value.every(i => i.length <= 32));
const areRestrictionsValid = computed(() => restrictions.value.every(r => r.length <= 32));
const isInterestEntered = computed(() => interests.value.some(i => i.trim() !== ""));


const validateInputs = () => {
  if (!isTitleValid.value) {
    alert("Title length must be 64 characters or less.");
    return false;
  }
  if (!isDescriptionValid.value) {
    alert("Description length must be 180 characters or less.");
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

const generateAIResponse = async () => {
  if (!validateInputs()) return;

  try {
    const authToken = localStorage.getItem("vue-token");
    const requestBody = {
      userId: userId.value,
      interests: interests.value,
      dietaryRestrictions: restrictions.value,
    };

    const response = await axios.post(
      `${API_CONFIG.baseURL}/ai/generate`,
      requestBody,
      {
        headers: {
          Authorization: `Bearer ${authToken}`,
          "Content-Type": "application/json",
        },
      }
    );

    description.value = response.data.data.idea.description;
    showAIPopup.value = true;

  } catch (error) {
    console.error("Error generating AI response:", error);
  }
};

const saveAsDraft = async () => {
  if (!validateInputs()) return;

  const authToken = localStorage.getItem("vue-token");
  const requestBody = {
    title: title.value,
    description: description.value,
    interests: interests.value,
    dietaryRestrictions: restrictions.value,
    userId: userId.value,
  };

  try {
    await axios.post(
      `${API_CONFIG.baseURL}/users/ideas/create-draft`,
      requestBody,
      {
        headers: {
          Authorization: `Bearer ${authToken}`,
          "Content-Type": "application/json",
        },
      }
    );
    alert("Draft saved successfully.");
  } catch (error) {
    console.error("Error saving draft:", error);
  }
};

const submitIdea = () => {
  if (!validateInputs()) return;

  console.log("Idea submitted:", {
    title: title.value,
    description: description.value,
    interests: interests.value,
    restrictions: restrictions.value,
  });
};
</script>


<template>
  <div class="w-full mx-auto p-6">
    <h2 class="text-2xl font-bold mb-10">Generate Idea</h2>

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
            Submit
          </button>

      </div>
    </form>
  </div>
</template>
