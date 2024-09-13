import './style.css';
import 'primeicons/primeicons.css';
import axios from 'axios';
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { keycloak, initKeycloak } from './keycloak/keycloak';

const app = createApp(App);

app.config.globalProperties.$keycloak = keycloak;
app.config.globalProperties.$logout = () => {
    keycloak.logout();
};

initKeycloak
    .then(() => {
        return axios.get('http://localhost:8081/users/jwtToken', {
            headers: {
                'Authorization': `Bearer ${keycloak.token}`
            }
        });
    })
    .then(response => {
        if (response.data.error === 'User does not have any roles assigned') {
            console.log("Routing to Unauthorized page");
            // Route to Unauthorized page after app is mounted
            app.use(router);
            app.mount('#app');
            router.replace({ name: 'Unauthorized' });
        } else {
            console.log("In else block");
            console.log("User details:", response.data);
            localStorage.setItem('user-details', JSON.stringify(response.data.user_details));
            localStorage.setItem('vue-token', keycloak.token);

            // Continue to main application logic if authorized
            app.use(router);
            app.mount('#app');
            router.replace({ name: 'Home' });
        }
    })
    .catch(error => {
        console.error('Error during app initialization:', error);
        // Optionally, route to an error page or handle the error
    });
