<template>
  <div>
    <!-- <DefaultLayout v-if="userProfileExists === true" /> -->
    <DefaultLayout v-if="userProfileExists && createProfileRoleExists" />
    <UserProfile v-if="createProfileRoleExists && !userProfileExists" />
    <UnauthorizedPage v-if="!createProfileRoleExists" />
   
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { initKeycloak } from './Keycloak';
import DefaultLayout from './layouts/DefaultLayout.vue';
import UserProfile from './views/UserProfile.vue';
import { useRouter } from 'vue-router';
import UnauthorizedPage from './views/UnauthorizedPage.vue';

const token = ref(null);
const username = ref(null);
const userProfileExists = ref(null);
const createProfileRoleExists = ref(null);
const router = useRouter();

onMounted(async () => {
  try {
    const keycloak = await initKeycloak;

    const storedToken = localStorage.getItem('vue-token');
    token.value = storedToken;

    if (storedToken) {
      try {
        const response = await axios.get('http://localhost:8082/api/v1/jwtToken', {
          headers: {
            'Authorization': `Bearer ${storedToken}`
          }
        });

        const roles = response.data.roles;
        console.log('roles are:', roles);
        const targetRole = 'CREATE_USER_PROFILE';

        if (roles.includes(targetRole)) {
          createProfileRoleExists.value = true;
          console.log(`User has the ${targetRole} role.`);
        } else {
          console.log(`User doesn't have the ${targetRole} role.`);
          createProfileRoleExists.value = false;
        }

        const responseUsername = response.data.username;
        if (responseUsername) {
          localStorage.setItem('username', responseUsername);
          username.value = responseUsername;

          const userCheckResponse = await axios.get(`http://localhost:8082/api/v1/users/${responseUsername}`, {
            headers: {
              'Authorization': `Bearer ${storedToken}`
            }
          });
          userProfileExists.value = userCheckResponse.data._user_profile_completed;
        }
      } catch (error) {
        console.error('Error checking user:', error);
      }
    } else {
      console.error('No token found in localStorage');
    }
  } catch (error) {
    console.error('Keycloak initialization failed:', error);
  }
});
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