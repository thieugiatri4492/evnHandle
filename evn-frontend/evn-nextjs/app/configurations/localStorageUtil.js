export const KEY_TOKEN = "accessToken";

export const setToken = (token) => {
    console.log(typeof window);
    if (typeof window !== 'undefined') {
        console.log("Token after set:", token)
        localStorage.setItem(KEY_TOKEN, token);
    }
};

export const getToken = () => {
    if (typeof window !== 'undefined') {
        return localStorage.getItem(KEY_TOKEN);
    }
};

export const removeToken = () => {
    if (typeof window !== 'undefined') {
        return localStorage.removeItem(KEY_TOKEN);
    }
};