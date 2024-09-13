import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
      url: 'http://localhost:8083', realm: 'Yolo', clientId: 'yolo-chef', onLoad:'login-required'
});

const initKeycloak = new Promise((resolve, reject) => {
    keycloak.init({ onLoad: 'login-required', checkLoginIframe: false })
        .then(auth => {
            if (!auth) {
                window.location.reload();

            } else { 
               
               try {
                localStorage.setItem("vue-token", keycloak.token);
                localStorage.setItem("vue-refresh-token", keycloak.refreshToken);
            } catch (error) {
                console.error('Failed to store token in localStorage:', error);
            }
            
                
                setInterval(() => {
                    keycloak.updateToken(70).then(refreshed => {
                        if (refreshed) {
                            console.debug('Token refreshed: ' + refreshed);
                            localStorage.setItem("vue-token", keycloak.token);
                        } else {
                            console.warn('Token not refreshed');
                        }
                    }).catch(() => {
                        console.error('Failed to refresh token');
                    });
                }, 60000);

                resolve(keycloak);
            }
        }).catch(() => {
            reject('Failed to initialize Keycloak');
        });
});

export { keycloak, initKeycloak };