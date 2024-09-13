<script setup>
import { ref } from "vue";
import axios from "axios";

const emit = defineEmits(["close"]);

const title = ref("");
const description = ref("");
const interests = ref(["", "", ""]);
const restrictions = ref(["", "", ""]); 
const aiPrompt = ref("");
const aiResponse = ref(null);
const chatHistory = ref([]);

const toggleAIPopup = () => {
  emit("close");
};

const generateAIResponse = async () => {
  try {
    const authToken = localStorage.getItem("authToken");
    const prompt = aiPrompt.value;
    const requestBody = {
      message: prompt,
      interests: interests.value,
      dietaryRestrictions: restrictions.value,
    };

    const response = await axios.post(
      "http://localhost:8081/ai/generate",
      requestBody,
      {
        headers: {
          Authorization: `Bearer ${authToken}`,
          "Content-Type": "application/json",
        },
      }
    );

    const generatedIdea = response.data.idea;

    chatHistory.value.push({
      prompt: aiPrompt.value,
      response: generatedIdea,
    });

    aiResponse.value = generatedIdea;
    aiPrompt.value = "";
  } catch (error) {
    console.error("Error generating AI response:", error);
  }
};

const saveAsDraft = async () => {
  const authToken = localStorage.getItem("vue-token");
  const requestBody = {
    title: aiResponse.value.title,
    description:  aiResponse.value.description,
    interests: interests.value,
    dietaryRestrictions: restrictions.value,
  };

    const response = await axios.post(
      "http://localhost:8081/users/ideas/draft",
      requestBody,
      {
        headers: {
          Authorization: `Bearer ${authToken}`,
          "Content-Type": "application/json",
        },
      }
    );
    toggleAIPopup();
};

//Ahmad
const submitIdea = () => {
  console.log("Idea submitted:", {
    title: title.value,
    description: description.value,
    interests: interests.value,
    dietaryRestrictions: restrictions.value,
  });
};
</script>

<template>
  <div
    class="fixed inset-0 flex items-center justify-center bg-gray-900 bg-opacity-50 z-50"
  >
    <div class="bg-white rounded-lg p-6 shadow-lg w-lg md:max-w-5xl relative">
      <button
        @click="toggleAIPopup"
        class="absolute top-2 right-2 text-gray-500 hover:text-gray-700"
      >
        ✕
      </button>

      <h3 class="text-xl font-bold text-center mb-4">AI Idea Generator</h3>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div>
          <!-- Chat History -->
          <div class="h-80 overflow-y-auto bg-gray-100 p-4 rounded-lg mb-4">
            <div v-for="(chat, index) in chatHistory" :key="index" class="mb-4">
              <p class="text-gray-600">
                <strong>User:</strong> {{ chat.prompt }}
              </p>
              <p class="text-gray-800">
                <strong>AI:</strong> {{ chat.response.title }} -
                {{ chat.response.description }}
              </p>
            </div>
          </div>

          <!-- Prompt Input -->
          <div class="mb-4">
            <label for="aiPrompt" class="block text-gray-700 font-medium mb-2">
              Enter your AI prompt:
            </label>
            <div class="flex gap-3">
              <input
                v-model="aiPrompt"
                id="aiPrompt"
                type="text"
                placeholder="Describe your idea"
                class="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring focus:border-blue-300"
              />
              <button
                @click="generateAIResponse"
                class="p-3 w-12 bg-blue-600 text-white rounded-lg shadow hover:bg-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-300"
              >
                <i class="pi pi-arrow-up-right"></i>
              </button>
            </div>
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

      <div class="mt-6 flex justify-between">

        <div>
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
        <button
          @click="toggleAIPopup"
          class="px-4 py-2 border border-gray-700 text-gray-700 font-medium rounded-lg shadow hover:bg-gray-600 hover:text-white focus:outline-none focus:ring-2 focus:ring-gray-300"
        >
          Close
        </button>
      </div>
    </div>
  </div>
</template>
