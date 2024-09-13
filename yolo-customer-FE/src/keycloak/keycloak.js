import Keycloak from 'keycloak-js';

import { KEYCLOAK_CONFIG } from '../config';

const keycloak = new Keycloak({
    url: KEYCLOAK_CONFIG.url,
    realm: KEYCLOAK_CONFIG.realm,
    clientId: KEYCLOAK_CONFIG.clientId,
});

const initKeycloak = new Promise((resolve, reject) => {
    keycloak.init({ onLoad: 'login-required'})
        .then(auth => {
            if (!auth) {
                window.location.reload();
            } else { 
                localStorage.setItem("vue-token", keycloak.token);
                localStorage.setItem("vue-refresh-token", keycloak.refreshToken);
                
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