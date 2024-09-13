import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';
import ViewIdeas from '@/views/ViewIdeas.vue';
import ViewOrders from '@/views/ViewOrders.vue';
import ViewRecipesList from "@/views/ViewRecipesList.vue";
import ViewRecipeDetails from "@/views/ViewRecipeDetails.vue";
import UserProfile from '@/views/UserProfile.vue';
import GenerateRecipe from '@/views/GenerateRecipe.vue';
const routes = [
  { path: "/", name: "Home", component: Home },
  { path: "/ideas", name: "GenerateIdea", component: ViewIdeas },
  { path: "/recipes", name: "ViewRecipesList", component: ViewRecipesList },
  {
    path: "/recipe-details/:recipeId",
    name: "ViewRecipeDetails",
    component: ViewRecipeDetails,
  },

  {
    path: "/generateRecipe/:id",
    name: "GenerateRecipe",
    component: GenerateRecipe,
  },
  { path: "/orders", name: "ViewOrders", component: ViewOrders },

  { path: "/userprofile", name: "UserProfile", component: UserProfile },
  // { path: '/defaultlayout', name: 'DefaultLayout', component: DefaultLayout },
];  

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;