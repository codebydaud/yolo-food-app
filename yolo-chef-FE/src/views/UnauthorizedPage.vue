<script setup>
// Add script if necessary
</script>

<template>
  <div class="container">
    <div class="content">
      <h1 class="title">Unauthorized Access</h1>
      <p class="message">Sorry, you don't have permission to view this page.</p>
      <button @click="$logout" class="logout-button">Logout</button>
    </div>
  </div>
</template>

<style scoped>
/* Center the container in the viewport */
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  padding: 20px;
  margin-left: 200px;
}

.content {
  text-align: center;
  border-radius: 8px;
  box-shadow: 0 16px 32px rgba(0, 0, 0, 0.1);
  padding: 40px;
  max-width: 400px;
  width: 100%;
  margin-bottom: 100px;
}

.title {
  font-size: 2rem;
  color: #333;
  margin-bottom: 20px;
}

.message {
  font-size: 1rem;
  color: #666;
  margin-bottom: 30px;
}

.logout-button {
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.logout-button:hover {
  background-color: #0056b3;
}

.logout-button:active {
  transform: scale(0.98);
}
</style>
