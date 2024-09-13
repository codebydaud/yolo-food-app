<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import { roles } from '../data/roles.js'; // Import the predefined roles
import { API_CONFIG } from '../config.js';

const orderData = ref({ orders: [] });
const selectedStatus = ref('');
const showModal = ref(false);
const selectedOrder = ref({});
const userDetails = ref({});
const userRoles = ref([]);
const hasAccess = ref(false);
const token = localStorage.getItem('vue-token');

const orderStatusEnum = {
  1: 'Placed',
  2: 'Processing',
  3: 'Dispatched',
};

const storedUserDetails = localStorage.getItem('user-details');
if (storedUserDetails) {
  userDetails.value = JSON.parse(storedUserDetails);
  userRoles.value = userDetails.value.roles || [];
} else {
  console.warn('No user details found in local storage.');
}

function hasRole(role) {
  return userRoles.value.includes(role);
}

function checkAccess() {
  if (hasRole(roles.VIEW_ORDER_HISTORY)) {
    hasAccess.value = true;
    fetchOrders();
  } else {

    window.location.href = '/error';
  }
}


window.addEventListener('storage', (event) => {
  if (event.key === 'vue-token') {
    token.value = event.newValue;
  }
});

const axiosInstance = axios.create({
  baseURL: API_CONFIG.baseURL,
  headers: {
    Authorization: `Bearer ${token}`,
  },
});

async function fetchOrders() {
  try {
    const response = await axiosInstance.get('/users/orders');
    orderData.value.orders = response.data.orders;
  } catch (error) {
    console.error('Failed to fetch orders:', error);
  }
}

async function fetchOrderItems(orderId) {
  try {
    const response = await axiosInstance.get(`/users/orders/${orderId}/orderitems`);
    selectedOrder.value.order_items = response.data.orderItems;
  } catch (error) {
    console.error('Failed to fetch order items:', error);
  }
}

const filteredOrders = computed(() => {
  if (!selectedStatus.value) {
    return orderData.value.orders;
  }
  return orderData.value.orders.filter(
    (order) => order.orderStatusId === parseInt(selectedStatus.value)
  );
});

function setStatusFilter(status) {
  selectedStatus.value = status;
}

function showOrderDetails(order) {
  selectedOrder.value = order;
  showModal.value = true;
  fetchOrderItems(order.id);
}

function closeModal() {
  showModal.value = false;
  selectedOrder.value = {};
}

onMounted(() => {
  checkAccess();
});
</script>

<template>
  <div class="container mx-auto p-4">
    <h1 class="text-2xl font-bold mb-4">Order History</h1>

    <!-- Filter Options -->
    <div class="mb-4 flex justify-end space-x-4">
      <button @click="setStatusFilter('1')"
        :class="selectedStatus === '1' ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'"
        class="px-4 py-2 rounded-lg focus:outline-none w-32 text-center">
        {{ orderStatusEnum[1] }}
      </button>
      <button @click="setStatusFilter('2')"
        :class="selectedStatus === '2' ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'"
        class="px-4 py-2 rounded-lg focus:outline-none w-32 text-center">
        {{ orderStatusEnum[2] }}
      </button>
      <button @click="setStatusFilter('3')"
        :class="selectedStatus === '3' ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'"
        class="px-4 py-2 rounded-lg focus:outline-none w-32 text-center">
        {{ orderStatusEnum[3] }}
      </button>
      <button @click="setStatusFilter('')"
        :class="selectedStatus === '' ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'"
        class="px-4 py-2 rounded-lg focus:outline-none w-32 text-center">
        All
      </button>
    </div>

    <!-- Order List with Headers -->
    <div class="bg-white shadow rounded-lg">
      <div class="grid grid-cols-5 gap-4 bg-gray-100 p-4 text-gray-700 font-semibold">
        <div>Order Code</div>
        <div>Amount</div>
        <div>Status</div>
        <div>User ID</div>
        <div></div> <!-- Empty header for the Details button -->
      </div>
      <div v-for="order in filteredOrders" :key="order.id" class="grid grid-cols-5 gap-4 p-4 border-b">
        <div>{{ order.code }}</div>
        <div>{{ order.price }}</div>
        <div>{{ orderStatusEnum[order.orderStatusId] }}</div>
        <div>{{ order.userId }}</div>
        <div class="text-right"><button @click="showOrderDetails(order)"
            class="px-4 py-2 bg-blue-500 text-white rounded">Details</button></div>
      </div>
    </div>

    <!-- Modal for Order Details -->
    <div v-if="showModal" class="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
      <div class="bg-white rounded-lg shadow-lg p-6 w-full max-w-3xl">
        <div class="flex justify-between items-center border-b pb-4">
          <h3 class="text-xl font-semibold">Order# {{ selectedOrder.code }}</h3>
          <button @click="closeModal" class="text-gray-600 hover:text-black">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24"
              stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>

        <div class="mt-4 grid grid-cols-2 gap-4">
          <!-- Order Information -->
          <div class="border p-4 rounded-lg shadow-sm">
            <p><strong>Amount:</strong> {{ selectedOrder.price }} USD</p>
            <p><strong>User ID:</strong> {{ selectedOrder.userId }}</p>
            <p><strong>Order Date:</strong> {{ new Date(selectedOrder.createdAt).toLocaleDateString() }}</p>
          </div>
          <!-- Shipping Information -->
          <div class="border p-4 rounded-lg shadow-sm">
            <p><strong>Status:</strong> {{ orderStatusEnum[selectedOrder.orderStatusId] }}</p>
            <p><strong>Delivery Address:</strong> {{ selectedOrder.paymentMethod }}</p>
            <p><strong>Delivery Date:</strong> {{ new Date(selectedOrder.updatedAt).toLocaleDateString() }}</p>
          </div>
        </div>

        <!-- Order Items -->
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
                <div> {{ new Date(item.orderItem.createdAt).toLocaleDateString() }}</div>
              </div>
            </li>
          </ul>
        </div>

        <div class="flex justify-end mt-4">
          <button @click="closeModal" class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600">Close</button>
        </div>
      </div>
    </div>

  </div>
</template>

<style>
/* Add custom styles if needed */
</style>
