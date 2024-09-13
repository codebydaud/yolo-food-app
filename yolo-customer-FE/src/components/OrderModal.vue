<template>
    <div v-if="showModal" class="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
      <div class="bg-white rounded-lg shadow-lg p-6 w-full max-w-3xl">
        <div class="flex justify-between items-center border-b pb-4">
          <h3 class="text-xl font-semibold">Order# {{ selectedOrder.code }}</h3>
          <button @click="closeModal" class="text-gray-600 hover:text-black">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
  
        <div class="mt-4 grid grid-cols-2 gap-4">
          <div class="border p-4 rounded-lg shadow-sm">
            <p><strong>Amount:</strong> {{ selectedOrder.price }} USD</p>
            <p><strong>User ID:</strong> {{ selectedOrder.userId }}</p>
            <p><strong>Order Date:</strong> {{ new Date(selectedOrder.createdAt).toLocaleDateString() }}</p>
          </div>
          <div class="border p-4 rounded-lg shadow-sm">
            <p><strong>Status:</strong> {{ orderStatusEnum[selectedOrder.orderStatusId] }}</p>
            <p><strong>Delivery Address:</strong> {{ selectedOrder.paymentMethod }}</p>
            <p><strong>Delivery Date:</strong> {{ new Date(selectedOrder.updatedAt).toLocaleDateString() }}</p>
          </div>
        </div>
  
        <h4 class="font-semibold mt-6">Order Items</h4>
        <div class="border-t mt-2 pt-2">
          <div class="grid grid-cols-7 gap-4 bg-gray-100 p-4 text-gray-700 font-semibold">
            <div>Recipe</div>
            <div>Recipe code</div>
            <div>Description</div>
            <div>Quantity</div>
            <div>Price</div>
            <div>Serving Size</div>
            <div>Created At</div>
          </div>
          <ul class="space-y-4">
            <li v-for="item in selectedOrder.order_items" :key="item.orderItem.id"
                class="flex justify-between items-center border-b py-4">
              <div class="grid grid-cols-7 gap-4 p-4">
                <div>{{ item.recipe.name }}</div>
                <div>{{ item.recipe.code }}</div>
                <div>{{ item.recipe.description }}</div>
                <div>{{ item.orderItem.quantity }}</div>
                <div>{{ item.recipe.price }} USD</div>
                <div>{{ item.recipe.servingSize }}</div>
                <div>{{ new Date(item.orderItem.createdAt).toLocaleDateString() }}</div>
              </div>
            </li>
          </ul>
        </div>
  
        <div class="flex justify-end mt-4">
          <button @click="closeModal" class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600">Close</button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { defineProps, emit } from 'vue';
  
  const props = defineProps({
    selectedOrder: {
      type: Object,
      required: true,
    },
    showModal: {
      type: Boolean,
      required: true,
    },
  });
  
  const emit = defineEmits(['close']);
  
  function closeModal() {
    emit('close');
  }
  </script>
  
  <style scoped>
  /* Add custom styles if needed */
  </style>
  