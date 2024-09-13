<template>
  <div class="container mx-auto p-6">
    <div v-if="userProfile" class="bg-white p-6 rounded-lg shadow-md text-center">
      <h1 class="text-2xl font-bold mb-4">User Profile</h1>
      <div class="mb-4">
        <label class="block text-gray-700 font-medium">Username:</label>
        <p class="text-gray-900">{{ userDetails.username }}</p>
      </div>
      <div class="mb-4">
        <label class="block text-gray-700 font-medium">Email:</label>
        <p class="text-gray-900">{{ userDetails.email }}</p>
      </div>
      <div class="mb-4">
        <label class="block text-gray-700 font-medium">First Name:</label>
        <p class="text-gray-900">{{ userProfile.first_name }}</p>
      </div>
      <div class="mb-4">
        <label class="block text-gray-700 font-medium">Last Name:</label>
        <p class="text-gray-900">{{ userProfile.last_name }}</p>
      </div>
      <div class="mb-4">
        <label class="block text-gray-700 font-medium">Contact Number:</label>
        <p class="text-gray-900">{{ userProfile.contact_number }}</p>
      </div>
      <div class="mb-4">
        <label class="block text-gray-700 font-medium">Address:</label>
        <p class="text-gray-900">{{ userProfile.house }}, {{ userProfile.street }}, {{ userProfile.area }}, {{ userProfile.city }}, {{ userProfile.country }} - {{ userProfile.zip_code }}</p>
      </div>
      <div class="mb-4">
        <label class="block text-gray-700 font-medium">Currency Code:</label>
        <p class="text-gray-900">{{ userProfile.currency_code }}</p>
      </div>
    </div>
    <div v-else class="text-center text-gray-500">Loading...</div>
    <div v-if="error" class="text-red-500 text-center mt-4">{{ error }}</div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userProfile: null,
      userDetails: {
        username: '',
        email: ''
      },
      error: null
    };
  },
  async created() {
    try {
      const token = localStorage.getItem("vue-token");
      const response = await axios.get('http://localhost:8081/users/profile', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      this.userProfile = response.data;

      const storedDetails = JSON.parse(localStorage.getItem('user-details'));
      if (storedDetails) {
        this.userDetails.username = storedDetails.username;
        this.userDetails.email = storedDetails.email;
      }
    } catch (error) {
      this.error = 'Failed to fetch user profile';
    }
  }
};
</script>

<style scoped>
/* Add any custom styles if needed */
</style>
