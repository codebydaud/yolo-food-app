import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import './style.css';
import 'primeicons/primeicons.css';
import Toast, { POSITION } from 'vue-toastification';
import 'vue-toastification/dist/index.css';

// Create and configure Vue app without Keycloak
const app = createApp(App);

app.use(router);

app.use(Toast, {
    position: POSITION.TOP_RIGHT,
    timeout: 3000
});

app.mount('#app');
