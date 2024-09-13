<script setup>
import { useRoute } from "vue-router";

const route = useRoute();

const isActiveLink = (link) => {
  return route.path === link;
};
</script>

<template>
  <nav
    class="hidden md:block w-64 bg-blue-600 text-white flex-col p-4 space-y-4 rounded-tr-2xl"
  >
    <h1 class="block py-2 px-4 text-3xl font-bold text-white mb-8">YOLO</h1>

    <!-- Router Links -->
    <router-link
      to="/"
      class="block py-2 px-4 font-semibold rounded"
      :class="
        isActiveLink('/')
          ? 'bg-white text-black'
          : 'hover:bg-blue-500 text-white'
      "
    >
      Home
    </router-link>

    <router-link
      to="/ideas"
      class="block py-2 px-4 font-semibold rounded"
      :class="
        isActiveLink('/ideas')
          ? 'bg-white text-black'
          : 'hover:bg-blue-500 text-white'
      "
    >
      Ideas
    </router-link>

    <router-link
      to="/recipes"
      class="block py-2 px-4 font-semibold rounded"
      :class="
        isActiveLink('/recipes')
          ? 'bg-white text-black'
          : 'hover:bg-blue-500 text-white'
      "
    >
      Recipes
    </router-link>

    <router-link
      to="/orders"
      class="block py-2 px-4 font-semibold rounded"
      :class="
        isActiveLink('/orders')
          ? 'bg-white text-black'
          : 'hover:bg-blue-500 text-white'
      "
    >
      View Orders
    </router-link>
    <router-link
      to="/generateRecipe"
      class="block py-2 px-4 font-semibold rounded"
      :class="
        isActiveLink('/generateRecipe')
          ? 'bg-white text-black'
          : 'hover:bg-blue-500 text-white'
      "
    >
      Generate Recipies
    </router-link>
  </nav>
</template>
