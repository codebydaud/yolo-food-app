<template>
  <div>
    <h1>Home Page</h1>
    <ProfileModal
      :isOpen="showProfileModal"
      @profile-completed="handleProfileCompleted"
    />
   
  </div>
</template>

<script>
import ProfileModal from "./ProfileModal.vue";
import axios from "axios";

export default {
  components: {
    ProfileModal
  },
  data() {
    return {
      showProfileModal: false,
      showUnallocatedRightsModal: false,
    };
  },
  mounted() {
    // Check user profile and roles when the component is mounted
    this.checkUserProfileAndRoles();
  },
  methods: {
    async checkUserProfileAndRoles() {
      try {
        const token = localStorage.getItem("vue-token");

        // Fetch user profile completion status
        const profileResponse = await axios.get(
          "http://localhost:8081/users/profiles",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        if (profileResponse.data.is_user_profile_completed === false) {
          this.showProfileModal = true;
        }
        
      } catch (error) {
        console.error("Error checking user profile and roles:", error);
      }
    },
    handleProfileCompleted() {
      this.showProfileModal = false;
      // Additional logic if needed after profile is completed
    },
    
  },
};
</script>
