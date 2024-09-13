<script setup>
import { useRoute } from 'vue-router';
import DefaultLayout from '@/layouts/DefaultLayout.vue';

const route = useRoute();
</script>

<template>
  <div>
    <!-- Use DefaultLayout for all routes except Unauthorized -->
    <template v-if="route.name !== 'Unauthorized'">
      <DefaultLayout />
    </template>
    <!-- For Unauthorized route, just render the router-view -->
    <template v-else>
      <router-view />
    </template>
  </div>
</template>
