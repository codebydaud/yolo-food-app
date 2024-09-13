<template>
  <div class="container mx-auto p-6 md:p-12 bg-white rounded-lg shadow-lg h-auto md:h-[900px]">
    <header class="flex justify-between items-center mb-6">
      <!-- Header content -->
    </header>

    <!-- Main content area for centering the form -->
    <div class="flex justify-center items-center h-full">
      <form class="w-full max-w-2xl h-auto md:h-[755px] p-6 md:p-12 bg-gray-100 rounded-lg shadow-md">
        <h3 class="text-xl md:text-2xl font-bold text-blue-600 mb-4 md:mb-6">Idea Details</h3>
        <div class="mb-4 md:mb-6">
          <label for="ideaTitle" class="block text-sm font-semibold mb-1">Idea Title:</label>
          <input v-model="ideaForm.title" id="ideaTitle" type="text" disabled class="w-full p-2 border border-gray-300 rounded" />
        </div>

        <div class="mb-4 md:mb-6">
          <label for="ideaDescription" class="block text-sm font-semibold mb-1">Description:</label>
          <textarea v-model="ideaForm.description" id="ideaDescription" disabled class="w-full p-2 border border-gray-300 rounded resize-y" />
        </div>

        <div class="mb-4 md:mb-6">
          <label for="customerName" class="block text-sm font-semibold mb-1">Customer Name:</label>
          <input v-model="ideaForm.customerName" id="customerName" type="text" disabled class="w-full p-2 border border-gray-300 rounded" />
        </div>

        <div class="mb-4 md:mb-6">
          <label class="block text-sm font-semibold mb-1">Interests:</label>
          <input v-model="ideaForm.interests" id="customerName" type="text" disabled class="w-full p-2 border border-gray-300 rounded" />
        </div>

        <div class="mb-4 md:mb-6">
            <label class="block text-sm font-semibold mb-1">Dietary Restrictions:</label>
            <input v-model="ideaForm.dietaryRestrictions" id="customerName" type="text" disabled class="w-full p-2 border border-gray-300 rounded" />
        </div>

        <!-- Button to Show Recipe Form -->
        <button v-if="!showRecipeForm" type="button" @click="showPopup = true" class="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition">Generate Recipe</button>
      </form>
    </div>

    <div v-if="showPopup" class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50" @click.self="showPopup=false">
  <div class="bg-white p-6 rounded-lg shadow-lg" style="max-width: 800px; height: 900px; width: 100%;"> <!-- Custom width -->
    <div class="flex items-center justify-between mb-4">
      <h2 class="text-xl font-bold text-blue-600">Generate Recipe</h2>
          <button :disabled="clickCount >= maxClicks" type="button" @click="AIOnClick" class="bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700 transition">Help me?</button>
         <p v-if="clickCount >= maxClicks" class="text-red-500">
      You have reached the maximum number of clicks.
    </p>
        </div>
        <form @submit.prevent="submitAIHelpForm" class="md:h-[650px] ">
          <!-- Form fields -->

          <div class="mb-4 md:mb-6">
            <label for="aiRecipeName" class="block text-sm font-semibold mb-1">Name:</label>
            <input v-model="aiForm.recipeName" id="aiRecipeName" type="text" required class="w-full p-2 border border-gray-300 rounded" :disabled="AiFormDisabled"/>
          </div>

          <div class="mb-4 md:mb-6">
            <label for="aiRecipeDescription" class="block text-sm font-semibold mb-1">Description:</label>
            <textarea v-model="aiForm.recipedescription" id="aiRecipeDescription" required class="w-full p-2 border border-gray-300 rounded resize-y" :style="{ height: '150px' }" :disabled="AiFormDisabled"/>

          </div>

          <div class="mb-4 md:mb-6">
            <label for="aiPrice" class="block text-sm font-semibold mb-1">Price:</label>
            <input v-model.number="aiForm.recipeprice" id="aiPrice" type="number" step="0.01" min="1" required class="w-full p-2 border border-gray-300 rounded" :disabled="AiFormDisabled"/>
          </div>

          <div class="mb-4 md:mb-6">
            <label for="aiServingSize" class="block text-sm font-semibold mb-1">Serving Size:</label>
            <input v-model.number="aiForm.recipeservingSize" id="aiServingSize" type="number" min="1" required class="w-full p-2 border border-gray-300 rounded" :disabled="AiFormDisabled"/>
          </div>

          <div class="mb-4 md:mb-6">
          <div>
            <label for="aiRecipeImages" class="block text-sm font-semibold mb-1 text-center">Images:</label>
  <div class="relative text-center">
    <input 
      @change="handleAIFileChange" 
      id="aiRecipeImages" 
      type="file" 
      accept="image/*" 
      multiple 
      class="absolute inset-0 opacity-0 cursor-pointer" 
      :disabled="AiFormDisabled"
    />
    <label 
      for="aiRecipeImages" 
      class="inline-block bg-blue-600 text-white py-2 px-4 rounded cursor-pointer hover:bg-blue-700 transition"
      style="max-width: 200px;"
    >
      Choose Files
    </label>
  </div>

  <div class="mt-2 flex flex-wrap gap-2">
    <div v-for="(preview, index) in aiForm.imagePreviews" :key="index" class="text-center">
      <img :src="preview.url" alt="Image preview" class="w-32 h-auto border border-gray-300 rounded" />
      <p class="text-sm mt-1">{{ preview.name }}</p> <!-- Display image name -->
    </div>
  </div>
  </div>

            <button v-if="showAIUpdateButton" type="button" @click="AIupdateRecipe" class="bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700 transition">Update</button>
          </div>

          <div class="flex flex-col md:flex-row justify-between mt-4">
            <button @click="cancelForm"  type="button" class="bg-red-600 text-white py-2 px-4 rounded hover:bg-red-700 transition ml-2">Cancel</button>
            <button v-if="AIshowUpdateDeleteButtons" type="button" @click="AIdeleteRecipe" class="bg-red-600 text-white py-2 px-4 rounded hover:bg-red-700 transition">Delete </button>
            <button v-if="AIshowUpdateDeleteButtons" type="button" @click="AIEditRecipe" class="bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700 transition">Edit </button>
            <button v-if="!AIshowUpdateDeleteButtons" type="submit" class="bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-800 transition">Save as Draft</button>
            <button v-if="AIshowSubmitButton" type="submit" @click="AIsaveRecipe" class="bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700 transition">Save </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>



<script setup>
import { ref,onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';

let ideaArray =  [];
const token = ref(null);
const route = useRoute();

const id = parseInt(route.params.id , 10); // Convert route param to integer
const idea = ref(null);
const loading = ref(true);

onMounted(async () => {
  try {
    
const storedToken = localStorage.getItem("vue-token");
    token.value = storedToken;
    
    const response = await axios.get(`http://localhost:8082/api/v1/ideas/${id}`, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${storedToken}`
    }} )
    idea.value = response.data.idea; // Access the 'idea' field from response data
    retrieveIdeaDetailFormData();
  } catch (error) {
    console.error('Error fetching idea:', error);
  } finally {
    loading.value = false;
  }
});
const retrieveIdeaDetailFormData = () => {
  if (idea.value) {
    ideaForm.value = {
      title: idea.value.title || '',
      description: idea.value.description || '',
      customerName: idea.value.customerName || '',
      dietaryRestrictions: Array.isArray(idea.value.dietaryRestrictions) ? idea.value.dietaryRestrictions : [].toString, // Ensure it's an array
      interests: Array.isArray(idea.value.interests) ? idea.value.interests : [].toString 
    }}}
const AiFormDisabled = ref(false);
var clickCount= 0; // To keep track of the number of clicks
var maxClicks=3
const showAIUpdateButton=ref(false);
const AIshowSubmitButton=ref(false);
const AIshowUpdateDeleteButtons = ref(false);


//AI
const AIsaveRecipe = () => {
  console.log("AI save");
  // Reset form fields
  AIshowUpdateDeleteButtons.value = false;
  AIshowSubmitButton.value=false;
  AiFormDisabled.value=false;
  aiForm.value.recipeName = '';
  aiForm.value.recipedescription = '';
  aiForm.value.recipeprice = null;
  aiForm.value.recipeservingSize = null;
  aiForm.value.images = [];
  aiForm.value.imagePreviews = [];
  
};
const AIOnClick=()=>{
  if (clickCount < maxClicks) {
        clickCount += 1;}
  const AIRequest = {
  aititle:ideaForm.value.title,
  aidescription:ideaForm.value.description,
  aiintrests:ideaForm.value.Intrests,
  aidietaryRestrictions:ideaForm.value.DietaryRestrictions}
;
const storedToken = localStorage.getItem("vue-token");
    token.value = storedToken;
   if(storedToken)
   {
  try {
    axios.post('http://localhost:8082/generateContent',AIRequest, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${storedToken}`
    }} )
    .then((res)=>{
      
       aiForm.value.recipeName = res.data.recipeName || '';
        aiForm.value.recipedescription = res.data.recipedescription || '';
         aiForm.value.recipeservingSize = res.data.recipeName || '';
        aiForm.value.recipeservingSize = res.data.recipeservingSize || '';
      

    }).catch((error) => {
      console.log(error)
      if (error.response && error.response.status === 400) {
        alert("Try Again");
      }
          
        });
  } catch (error) {
    alert("Try Again");
    console.error('Error submitting AI Help form', error);
  }}
}
const AIupdateRecipe = () => {
  try {
    const formData = new FormData();
  formData.append('name', aiForm.value.recipeName);
  formData.append('description', aiForm.value.recipedescription);
  formData.append('price', aiForm.value.recipeprice);
  formData.append('serving_size', aiForm.value.recipeservingSize);
    const storedToken = localStorage.getItem("vue-token");
    token.value = storedToken;
   if(storedToken)
   {
    const recipeId=localStorage.getItem("recipeId");
    console.log("Esha",recipeId)
    axios.post(`http://localhost:8082/api/v1/recipes/${recipeId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': `Bearer ${storedToken}`
      }
    }).then((res)=>{
      if(res.status==200)
    {
      localStorage.removeItem(recipeId)
      AiFormDisabled.value=true;
    AIshowSubmitButton.value=true;
    showAIUpdateButton.value=false;
    }
    
    });}
  } catch (error) {
    console.error('Error updating AI Help form', error);
  }
  
  
  // Logic to update the recipe
};
const AIEditRecipe = () => {
  AiFormDisabled.value=false;
  AIshowSubmitButton.value=false;
  showAIUpdateButton.value=true;
  // Logic to update the recipe
};

const AIdeleteRecipe = () => {
  console.log("Delete");
  const storedToken = localStorage.getItem("vue-token");
    token.value = storedToken;
   if(storedToken)
   {
  const recipeId=localStorage.getItem("recipeId");
  axios.delete(`http://localhost:8082/api/v1/recipes/${recipeId}`, {
      headers: {
        'Authorization': `Bearer ${storedToken}`
      }
    }).then((res)=>{
      
      if(res.status==200)
    {
      AIshowUpdateDeleteButtons.value = true;
      AIshowSubmitButton.value=true;
      AiFormDisabled.value = true;
      localStorage.setItem("recipeId",res.data.id)
console.log(res.data.id)
    }
  
    });
  }// Logic to delete the recipe
};
// Idea form data
const ideaForm = ref({
  title: '',
  description: '',
  customerName: '',
  DietaryRestrictions:'',
    Intrests:''
});


const aiForm = ref({
  recipeName: '',
  recipedescription: '',
  recipeprice: null,
  recipeservingSize: null,
  images: [],
  imagePreviews: []
});

const showPopup = ref(false);
const showRecipeForm = ref(false);
const handleAIFileChange = (event) => {
  const files = Array.from(event.target.files);
  aiForm.value.images = files;

  aiForm.value.imagePreviews = files.map(file => {
    const reader = new FileReader();
    return new Promise((resolve) => {
      reader.onload = (e) => {
        resolve({
          url: e.target.result,
          name: file.name // Store the file name along with the URL
        });
      };
      reader.readAsDataURL(file);
    });
  });

  Promise.all(aiForm.value.imagePreviews).then(previews => {
    aiForm.value.imagePreviews = previews;
  });
};


const submitAIHelpForm = async () => {
      
  if (aiForm.value.images.length === 0) {
    alert('Please select at least one image.');
    return;
  }

  const formData = new FormData();
  formData.append('name', aiForm.value.recipeName);
  formData.append('description', aiForm.value.recipedescription);
  formData.append('price', aiForm.value.recipeprice);
  formData.append('serving_size', aiForm.value.recipeservingSize);

  for (const image of aiForm.value.images) {
    formData.append('images', image);

  }

  try {
    const storedToken = localStorage.getItem("vue-token");
    token.value = storedToken;
   if(storedToken)
   {
    const response = await axios.post(`http://localhost:8082/api/v1/ideas/${id}/recipes`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': `Bearer ${storedToken}`
      }
    }).then((res)=>{
      
      if(res.status==201)
    {
      AIshowUpdateDeleteButtons.value = true;
      AIshowSubmitButton.value=true;
      AiFormDisabled.value = true;
      localStorage.setItem("recipeId",res.data.id)
console.log(res.data.id)
    }
      
    });}
  } catch (error) {
    console.error('Error submitting AI Help form', error);
  }
};

</script>

<style >

</style>






