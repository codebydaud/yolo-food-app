import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';
import GenerateIdea from '@/views/GenerateIdea.vue';
import ViewIdeas from '@/views/ViewIdeas.vue';
import ViewOrders from '@/views/ViewOrders.vue';
import Profile from '@/views/Profile.vue';
import Error from '@/views/Error.vue';
import Unauthorized from '@/views/Unauthorized.vue';
import ViewRecipesList from '@/views/ViewRecipesList.vue';
import ViewRecipeDetails from '../views/ViewRecipeDetails.vue';

const routes = [
 
  { path: '/', name: 'Home', component: Home },
  { path: '/generate-idea', name: 'GenerateIdea', component: GenerateIdea },
  { path: '/view-ideas', name: 'ViewIdeas', component: ViewIdeas },
  { path: '/view-orders', name: 'ViewOrders', component: ViewOrders },
  { path: '/profile', name: 'Profile', component: Profile },
  { path: '/error', name: 'Error', component: Error },
  { path: "/recipes", name: "ViewRecipesList", component: ViewRecipesList },
  {
    path: "/recipe-details/:recipeId",
    name: "ViewRecipeDetails",
    component: ViewRecipeDetails,
  },
  { path: '/unauthorized', name: 'Unauthorized', component: Unauthorized }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
