<template>
  <div class="container mx-auto p-6 max-w-3xl">
    <div class="bg-white p-8 rounded-lg shadow-lg border border-gray-200">
      <!-- Profile Header -->
      <div class="text-center mb-6">
        <h1 class="text-3xl font-extrabold text-gray-900 mb-2">User Profile</h1>
      </div>

      <!-- Profile Information -->
      <div class="grid grid-cols-1 gap-6">
        <!-- Username -->
        <div class="flex items-center justify-between p-4 bg-gray-50 rounded-lg border border-gray-200">
          <span class="text-gray-700 font-semibold">Username:</span>
          <span class="text-gray-900">{{ userDetails.username }}</span>
        </div>

        <!-- Email -->
        <div class="flex items-center justify-between p-4 bg-gray-50 rounded-lg border border-gray-200">
          <span class="text-gray-700 font-semibold">Email:</span>
          <span class="text-gray-900">{{ userDetails.email }}</span>
        </div>

        <!-- First Name -->
        <div class="flex items-center justify-between p-4 bg-gray-50 rounded-lg border border-gray-200">
          <span class="text-gray-700 font-semibold">First Name:</span>
          <span class="text-gray-900">{{ userProfile.first_name }}</span>
        </div>

        <!-- Last Name -->
        <div class="flex items-center justify-between p-4 bg-gray-50 rounded-lg border border-gray-200">
          <span class="text-gray-700 font-semibold">Last Name:</span>
          <span class="text-gray-900">{{ userProfile.last_name }}</span>
        </div>

        <!-- Contact Number -->
        <div class="flex items-center justify-between p-4 bg-gray-50 rounded-lg border border-gray-200">
          <span class="text-gray-700 font-semibold">Contact Number:</span>
          <span class="text-gray-900">{{ userProfile.contact_number }}</span>
        </div>

        <!-- Address -->
        <div class="flex flex-col p-4 bg-gray-50 rounded-lg border border-gray-200">
          <span class="text-gray-700 font-semibold mb-1">Address:</span>
          <span class="text-gray-900">{{ userProfile.house }}, {{ userProfile.street }}, {{ userProfile.area }}, {{ userProfile.city }}, {{ userProfile.country }} - {{ userProfile.zip_code }}</span>
        </div>

        <!-- Currency Code -->
        <div class="flex items-center justify-between p-4 bg-gray-50 rounded-lg border border-gray-200">
          <span class="text-gray-700 font-semibold">Currency Code:</span>
          <span class="text-gray-900">{{ userProfile.currency_code }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userProfile: {
        first_name: 'Arbaz',
        last_name: ' Ahmad',
        contact_number: '1234567890123',
        house: '123',
        street: 'Main St',
        area: 'Model Town',
        city: 'Lahore',
        country: 'Pakistan',
        zip_code: '12345',
        currency_code: 'USD',
      },
      userDetails: {
        username: 'arbazahmad',
        email: 'arbazahmad@gmail.com',
      },
    };
  },
};
</script>

<style scoped>
/* Add any custom styles if needed */
</style>
