<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";

// Initialize router
const router = useRouter();

// Dummy data for ideas
const dummyIdeas = [
  {
    id: 1,
    title: "Creamy Garlic Parmesan Pasta",
    description:
      "A rich and creamy garlic parmesan pasta that is easy to make and incredibly delicious. Perfect for a quick weeknight dinner.",
    statusId: 2,
    createdAt: "2024-01-01T12:00:00Z",
    updatedAt: "2024-01-02T12:00:00Z",
  },
  {
    id: 2,
    title: "Spicy Thai Basil Chicken",
    description:
      "A spicy and flavorful Thai basil chicken stir-fry with fresh basil leaves, bell peppers, and a savory sauce. A quick and satisfying meal.",
    statusId: 2, // Submit
    createdAt: "2024-02-01T12:00:00Z",
    updatedAt: "2024-02-02T12:00:00Z",
  },
  {
    id: 3,
    title: "Classic Beef Stroganoff",
    description:
      "Tender strips of beef simmered in a creamy mushroom sauce, served over egg noodles. A comforting and hearty dish that everyone will love.",
    statusId: 1,
    createdAt: "2024-03-01T12:00:00Z",
    updatedAt: "2024-03-02T12:00:00Z",
  },

  {
    id: 4,
    title: "Classic Pizza",
    description:
      "Classic Italian chicken pizza.",
    statusId: 1,
    createdAt: "2024-03-01T12:00:00Z",
    updatedAt: "2024-03-02T12:00:00Z",
  },

  {
    id: 5,
    title: "Classic Mumbai Biryani",
    description:
      "Classic chicken mumbai biryani.",
    statusId: 1,
    createdAt: "2024-03-01T12:00:00Z",
    updatedAt: "2024-03-02T12:00:00Z",
  },
];

// Vue refs and state variables
const ideaData = ref({ ideas: [] });
const selectedStatus = ref("");
const showModal = ref(false);
const selectedIdea = ref({});

const ideaStatusEnum = {
  1: "Draft",
  2: "Submitted",
};

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

// Set the idea status filter
function setStatusFilter(status) {
  selectedStatus.value = status;
}

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
      name: "ViewRecipesList",
    })
    .catch((err) => {
      console.error("Navigation error:", err);
    });
}

// Delete an idea
function deleteIdea() {
  if (confirm("Are you sure you want to delete this idea?")) {
    ideaData.value.ideas = ideaData.value.ideas.filter(
      (idea) => idea.id !== selectedIdea.value.id
    );
    closeModal();
  }
}

// Submit an idea
function submitIdea() {
  if (confirm("Are you sure you want to submit this idea?")) {
    selectedIdea.value.statusId = 2; // Change status to 'Submitted'
    const updatedIdea = ideaData.value.ideas.find(
      (idea) => idea.id === selectedIdea.value.id
    );
    if (updatedIdea) {
      updatedIdea.statusId = 2;
    }
    closeModal();
  }
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
    <h1 class="text-2xl font-bold mb-4">My Ideas</h1>

    <!-- Filter Options -->
    <div class="mb-4 flex justify-end space-x-4">
      <button
        @click="setStatusFilter('1')"
        :class="[
          selectedStatus === '1' ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700',
          'px-4 py-2 rounded-lg focus:outline-none w-32 text-center'
        ]"
      >
        {{ ideaStatusEnum[1] }}
      </button>
      <button
        @click="setStatusFilter('2')"
        :class="[
          selectedStatus === '2' ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700',
          'px-4 py-2 rounded-lg focus:outline-none w-32 text-center'
        ]"
      >
        {{ ideaStatusEnum[2] }}
      </button>
      <button
        @click="setStatusFilter('')"
        :class="[
          selectedStatus === '' ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700',
          'px-4 py-2 rounded-lg focus:outline-none w-32 text-center'
        ]"
      >
        All
      </button>
    </div>

    <!-- Idea List -->
    <div class="bg-white shadow rounded-lg">
      <div
        class="grid grid-cols-5 gap-4 bg-gray-100 p-4 text-gray-700 font-semibold"
      >
        <div>Title</div>
        <div>Description</div>
        <div>Status</div>
        <!-- Empty column for spacing -->
        <div></div>
        <div></div>
      </div>
      <div
        v-for="idea in filteredIdeas"
        :key="idea.id"
        class="grid grid-cols-5 gap-4 p-4 border-b items-center"
      >
        <div>{{ idea.title }}</div>
        <div>{{ truncatedDescription(idea.description) }}</div>
        <div>{{ ideaStatusEnum[idea.statusId] }}</div>
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
            :disabled="idea.statusId !== 2"
            :class="[
              'px-4 py-2 bg-blue-600 text-white rounded-lg shadow hover:bg-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-300',
              idea.statusId !== 2 ? 'cursor-not-allowed opacity-50' : 'cursor-pointer'
            ]"
          >
            View Recipes
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
              <strong>Status:</strong>
              {{ ideaStatusEnum[selectedIdea.statusId] }}
            </p>
            <p>
              <strong>Created At:</strong>
              {{ new Date(selectedIdea.createdAt).toLocaleDateString() }}
            </p>
          </div>
        </div>

        <!-- Modal Actions -->
        <div class="mt-4 flex justify-end space-x-2">
          <button
            @click="deleteIdea"
            :disabled="selectedIdea.statusId === 2"
            :class="[
              'px-4 py-2 bg-red-500 text-white rounded-lg',
              selectedIdea.statusId === 2 ? 'cursor-not-allowed opacity-50' : 'cursor-pointer'
            ]"
          >
            Delete
          </button>
          <button
            @click="submitIdea"
            :disabled="selectedIdea.statusId === 2"
            :class="[
              'px-4 py-2 bg-blue-600 text-white rounded-lg shadow hover:bg-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-300',
              selectedIdea.statusId === 2 ? 'cursor-not-allowed opacity-50' : 'cursor-pointer'
            ]"
          >
            Submit
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

