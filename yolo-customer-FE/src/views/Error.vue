<template>
    <div class="flex flex-col items-center justify-center h-screen bg-blue-50">
      <div class="text-center">
        <h1 class="text-6xl font-bold text-blue-600">Oops!</h1>
        <p class="mt-4 text-lg text-blue-500">Something went wrong.</p>
        <p class="mt-2 text-gray-600">We couldn't find the page you're looking for.</p>
        <img
          src="https://via.placeholder.com/300?text=Error+Image"
          alt="Error Image"
          class="mt-6 w-48 h-48 object-cover"
        />
        <div class="mt-8">
          <router-link
            to="/"
            class="px-4 py-2 font-semibold text-white bg-blue-600 rounded-lg hover:bg-blue-700 transition duration-300"
            >Go to Homepage</router-link
          >
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'ErrorPage',
  }
  </script>
  
  <style scoped>
  /* Add any additional styles if necessary */
  </style>
  