import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';
import ViewIdeas from '@/views/ViewIdeas.vue';
import ViewOrders from '@/views/ViewOrders.vue';
import ViewRecipesList from "@/views/ViewRecipesList.vue";
import ViewRecipeDetails from "@/views/ViewRecipeDetails.vue";
import GenerateRecipe from '@/views/GenerateRecipe.vue';
import ViewRecipesListDummy from '@/views/ViewRecipesListDummy.vue';
import Profile from '@/views/Profile.vue';
const routes = [
  { path: "/", name: "Home", component: Home },
  { path: "/ideas", name: "GenerateIdea", component: ViewIdeas },
  { path: "/recipes", name: "ViewRecipesList", component: ViewRecipesList },
  { path: "/recipes-", name: "ViewRecipesListDummy", component: ViewRecipesListDummy },
  { path: '/profile', name: 'Profile', component: Profile },
  {
    path: "/recipe-details/:recipeId",
    name: "ViewRecipeDetails",
    component: ViewRecipeDetails,
  },

  {
    path: "/generateRecipe",
    name: "GenerateRecipe",
    component: GenerateRecipe,
  },
  { path: "/orders", name: "ViewOrders", component: ViewOrders },

 
];  

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;