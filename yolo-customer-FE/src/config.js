export const KEYCLOAK_CONFIG = {
    url: import.meta.env.VITE_KEYCLOAK_URL || 'http://localhost:8080',
    realm: import.meta.env.VITE_KEYCLOAK_REALM,
    clientId: import.meta.env.VITE_KEYCLOAK_CLIENT_ID,
};

export const API_CONFIG = {
    baseURL: import.meta.env.SERVER_URL || 'http://localhost:8081',
};
