<template>
  <div class="unauthorized-page">
    <h1>Unauthorized Access</h1>
    <p>You do not have permission to view this page.</p>
    <button
          @click.prevent="$logout"
          class="w-full sm:w-auto ml-4 bg-red-500 text-white py-2 px-4 rounded-md shadow-sm hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-opacity-50"
        >
          Logout
        </button>
  </div>
</template>

<script>
export default {
  name: 'Unauthorized'
};
</script>

<style scoped>
.unauthorized-page {
  text-align: center;
  margin-top: 50px;
}

.unauthorized-page h1 {
  color: red;
  font-size: 2rem;
}

.unauthorized-page p {
  margin: 20px 0;
  font-size: 1.2rem;
}

.unauthorized-page a {
  color: #007bff;
  text-decoration: none;
}
</style>
