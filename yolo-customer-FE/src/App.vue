<template>
  <div>
    <DefaultLayout />
  </div>
</template>

<script setup>
import DefaultLayout from './layouts/DefaultLayout.vue';

// No onMounted logic needed since we're no longer checking for authorization
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
