<template>
  <div class="flex flex-col items-center justify-center min-h-screen p-4">
    <div class="w-full max-w-screen-lg grid grid-cols-3 gap-4 mb-6">
      <img
        src="@/assets/images/chef.png"
        alt="Chef"
        class="w-full h-auto object-cover rounded-lg shadow-lg max-h-60 md:max-h-80"
      />
      <img
        src="@/assets/images/bowl.png"
        alt="Bowl"
        class="w-full h-auto object-cover rounded-lg shadow-lg max-h-60 md:max-h-80"
      />
      <img
        src="@/assets/images/food.png"
        alt="Food"
        class="w-full h-auto object-cover rounded-lg shadow-lg max-h-60 md:max-h-80"
      />
    </div>
    <h1 class="mt-4 text-2xl md:text-4xl font-bold text-gray-800 text-center">
      Welcome to YOLO Chef!
    </h1>
  </div>
</template>

<script>
export default {
  // No need for components or data properties
};
</script>

<style scoped>
/* No custom CSS required when using Tailwind CSS */
</style>
