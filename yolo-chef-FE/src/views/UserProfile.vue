<template>
  <DefaultLayout v-if="formSubmitted === true" />
  <div v-if="!formSubmitted === true">
  <div class="flex w-screen h-screen  ">
      <div class="w-3/5 h-full bg-cover bg-center">
    <img :src="foodimage" class="w-full h-full object-cover" />
  </div>
    <form class="h-auto md:h-[755px] p-6 md:p-12 rounded-lg mt-20 ml-20">
      <h3 class="text-xl md:text-2xl font-bold text-blue-600 mb-4 md:mb-6">Create User Profile</h3>

      <div class="flex space-x-4 mb-4">
        <div class="flex-1">
          <label for="firstName" class="block text-sm font-semibold mb-1 text-left">First Name:</label>
          <input 
            v-model="ideaForm.firstName" 
            id="firstName" 
            name="firstName"
            type="text" 
            class="w-full p-2 border border-gray-300 rounded" 
          />
        </div>

        <div class="flex-1">
          <label for="lastName" class="block text-sm font-semibold mb-1 text-left">Last Name:</label>
          <input 
            v-model="ideaForm.lastName" 
            id="lastName" 
            name="lastName"
            type="text" 
            class="w-full p-2 border border-gray-300 rounded" 
          />
        </div>
      </div>

      <div class="flex space-x-4 mb-4">
        <div class="flex-[0_0_70%]">
          <label for="contactNumber" class="block text-sm font-semibold mb-1 text-left">Contact Number:</label>
          <input 
            v-model="ideaForm.contactNumber" 
            id="contactNumber" 
            name="contactNumber"
            type="text" 
            class="w-full p-2 border border-gray-300 rounded" 
          />
        </div>

        <div class="flex-[0_0_20%]">
          <label for="currency" class="block text-sm font-semibold mb-1 text-left">Currency:</label>
          <select 
            v-model="ideaForm.currency" 
            id="currency" 
            name="currency" 
            class="w-28 h-10 p-2 border border-gray-300 rounded"
          >
            <option value="USD">USD</option>
           
          </select>
        </div>
      </div>

      <div class="mb-4">
        <label for="country" class="block text-sm font-semibold mb-1 text-left">Country:</label>
        <input 
          v-model="ideaForm.country" 
          id="country" 
          name="country" 
          type="text" 
          class="w-full p-2 border border-gray-300 rounded" 
        />
      </div>

      <div class="mb-4">
        <label for="city" class="block text-sm font-semibold mb-1 text-left">City:</label>
        <input 
          v-model="ideaForm.city" 
          id="city" 
          name="city" 
          type="text" 
          class="w-full p-2 border border-gray-300 rounded" 
        />
      </div>

      <div class="flex space-x-4 mb-4">
        <div class="flex-1">
          <label for="area" class="block text-sm font-semibold mb-1 text-left">Area:</label>
          <input 
            v-model="ideaForm.area" 
            id="area" 
            name="area" 
            type="text" 
            class="w-full p-2 border border-gray-300 rounded" 
          />
        </div>

        <div class="flex-1">
          <label for="zipCode" class="block text-sm font-semibold mb-1 text-left">Zip Code:</label>
          <input 
            v-model="ideaForm.zipCode" 
            id="zipCode" 
            name="zipCode" 
            type="text" 
            class="w-full p-2 border border-gray-300 rounded" 
          />
        </div>
      </div>

      <div class="flex space-x-4 mb-16">
        <div class="flex-1">
          <label for="house" class="block text-sm font-semibold mb-1 text-left">House:</label>
          <input 
            v-model="ideaForm.house" 
            id="house" 
            name="house" 
            type="text" 
            class="w-full p-2 border border-gray-300 rounded" 
          />
        </div>

        <div class="flex-1">
          <label for="street" class="block text-sm font-semibold mb-1 text-left">Street:</label>
          <input 
            v-model="ideaForm.street" 
            id="street" 
            name="street" 
            type="text" 
            class="w-full p-2 border border-gray-300 rounded" 
          />
        </div>
      </div>

      <div class="flex space-x-4 mb-16">
        <button 
        type="button" 
        @click="handleSubmit" 
        class="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition"
      >
        Create User Profile
      </button>
      <button 
        type="button" 
           @click="$logout"
        class="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition"
      >
        Logout
      </button>
      </div>
     
     
    </form>
  </div>
</div>

</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import foodimage from '../assets/food.jpg';
import { useToast } from 'vue-toastification';
import DefaultLayout from '@/layouts/DefaultLayout.vue';

const ideaForm = ref({
  firstName: '',
  lastName: '',
  contactNumber: '',
  currency: 'USD',
  country: '',
  city: '',
  area: '',
  zipCode: '',
  house: '',
  street: ''
});

const toast = useToast();
const formSubmitted = ref(false); 
const currentComponent = ref(''); 
const userProfileCreated = ref(null);

const handleSubmit = async () => {
  try {
    const username = localStorage.getItem("username");
    const token = localStorage.getItem("vue-token");

    if (!username || !token) {
      toast.error('Missing username or token.');
      return;
    }

    const formData = {
      firstName: ideaForm.value.firstName,
      lastName: ideaForm.value.lastName,
      contactNumber: ideaForm.value.contactNumber,
      currency: ideaForm.value.currency,
      country: ideaForm.value.country,
      city: ideaForm.value.city,
      area: ideaForm.value.area,
      zipCode: ideaForm.value.zipCode,
      house: ideaForm.value.house,
      street: ideaForm.value.street
    };

    const response = await axios.post(`http://localhost:8082/api/v1/users/${username}/userProfiles`, formData, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
    if (response.data.message === 'User profile created successfully') {
  toast.success('User profile created successfully!');
  
 
  setTimeout(() => {
    formSubmitted.value = true;
  }, 2000); 
  
} else {
  toast.error('Failed to create user profile.');
}

  } catch (error) {
    console.error('Error creating user profile:', error);
    toast.error('An error occurred while creating user profile.');
  }
};
</script>


<style scoped>

</style>