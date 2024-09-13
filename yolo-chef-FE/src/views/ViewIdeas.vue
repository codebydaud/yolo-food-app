<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from 'vue-router';

const router = useRouter();

// Dummy data for ideas
const dummyIdeas = [
  {
    id: 1,
    name: "Muhammad Daud",
    title: "Creamy Garlic Parmesan Pasta",
    description:
      "A rich and creamy garlic parmesan pasta that is easy to make and incredibly delicious. Perfect for a quick weeknight dinner.",
    statusId: 2, // Draft
    createdAt: "2024-01-01T12:00:00Z",
    updatedAt: "2024-01-02T12:00:00Z",
  },
  {
    id: 2,
    name: "Muhammad Daud",
    title: "Spicy Thai Basil Chicken",
    description:
      "A spicy and flavorful Thai basil chicken stir-fry with fresh basil leaves, bell peppers, and a savory sauce. A quick and satisfying meal.",
    statusId: 2, // Submit
    createdAt: "2024-02-01T12:00:00Z",
    updatedAt: "2024-02-02T12:00:00Z",
  },
  {
    id: 3,
    name: "Muhammad Daud",
    title: "Classic Beef Stroganoff",
    description:
      "Tender strips of beef simmered in a creamy mushroom sauce, served over egg noodles. A comforting and hearty dish that everyone will love.",
    statusId: 1, // Draft
    createdAt: "2024-03-01T12:00:00Z",
    updatedAt: "2024-03-02T12:00:00Z",
  },
];

// Vue refs and state variables
const ideaData = ref({ ideas: [] });
const selectedStatus = ref("");
const showModal = ref(false);
const selectedIdea = ref({});

// Simulate fetching ideas from an API
async function fetchIdeas() {
  ideaData.value.ideas = dummyIdeas;
}

// Computed property to filter ideas by status
const filteredIdeas = computed(() => {
  if (!selectedStatus.value) {
    return ideaData.value.ideas;
  }
  return ideaData.value.ideas.filter(
    (idea) => idea.statusId === parseInt(selectedStatus.value)
  );
});

// Show idea details modal
function showIdeaDetails(idea) {
  selectedIdea.value = idea;
  showModal.value = true;
}

// Close the modal
function closeModal() {
  showModal.value = false;
  selectedIdea.value = {};
}

// View recipes for an idea
function viewRecipes(idea) {
  router
    .push({
      name: "ViewRecipesListDummy",
    })
    .catch((err) => {
      console.error("Navigation error:", err);
    });
}

// Generate recipe logic
function generateRecipe() {
 
  router.push({ name: 'GenerateRecipe' });
}

// Computed property to trim description for list view
const truncatedDescription = (description) => {
  const maxLength = 30; // Max length for truncated description
  return description.length > maxLength
    ? description.substring(0, maxLength) + "..."
    : description;
};

// Mounted lifecycle hook
onMounted(() => {
  fetchIdeas();
});
</script>
<template>
  <div class="container mx-auto p-4">
    <h1 class="text-2xl font-bold mb-4">Customer Ideas</h1>

    <!-- Idea List -->
    <div class="bg-white shadow rounded-lg">
      <div class="grid grid-cols-5 gap-4 bg-gray-100 p-4 text-gray-700 font-semibold">
        <div>Customer Name</div>
        <div>Idea Title</div>
        <div>Idea Description</div>
        <!-- Empty column for spacing -->
        <div></div>
        <div></div>
      </div>
      <div
        v-for="idea in filteredIdeas"
        :key="idea.id"
        class="grid grid-cols-5 gap-4 p-4 border-b items-center"
      >
        <div>{{ idea.name }}</div>
        <div>{{ idea.title }}</div>
        <div>{{ truncatedDescription(idea.description) }}</div>
        <!-- Empty column for spacing -->
        <div></div>
        <div class="flex justify-end space-x-2">
          <button
            @click="showIdeaDetails(idea)"
            class="px-4 py-2 bg-gray-800 text-white rounded-lg shadow hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-gray-300 mr-3"
          >
            Details
          </button>
          <button
            @click="viewRecipes(idea)"
            class="px-4 py-2 bg-blue-600 text-white rounded-lg shadow hover:bg-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-300"
          >
            My Recipes
          </button>
        </div>
      </div>
    </div>

    <!-- Modal for Idea Details -->
    <div
      v-if="showModal"
      class="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50"
    >
      <div class="bg-white rounded-lg shadow-lg p-6 w-full max-w-3xl">
        <div class="flex justify-between items-center border-b pb-4">
          <h3 class="text-xl font-semibold">Idea: {{ selectedIdea.title }}</h3>
          <button @click="closeModal" class="text-gray-600 hover:text-black">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-6 w-6"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
          </button>
        </div>

        <div class="mt-4">
          <!-- Idea Information -->
          <div class="border p-4 rounded-lg shadow-sm">
            <p><strong>Description:</strong> {{ selectedIdea.description }}</p>
            <p>
              <strong>Customer Name:</strong>
              {{ selectedIdea.name }}
            </p>
            <p>
              <strong>Created At:</strong>
              {{ new Date(selectedIdea.createdAt).toLocaleDateString() }}
            </p>
          </div>

          <!-- Generate Recipe Button -->
          <div class="mt-4 flex justify-end">
            <button
              @click="generateRecipe"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg shadow hover:bg-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-300"
            >
              Generate Recipe
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
