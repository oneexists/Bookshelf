import { APP_USERS_URL, AUTHENTICATE_URL, REFRESH_URL, TOKEN_KEY } from "../config/bookshelfApi";

export async function authenticate({ username, password }) {
    const response = await fetch(AUTHENTICATE_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, password })
    });

    if (response.status === 200) {
        const { jwt_token } = await response.json();
        return jwt_token;
    }
    return Promise.reject();
}

export async function refresh() {
    const token = localStorage.getItem(TOKEN_KEY);
    if (token) {
        const response = await fetch(REFRESH_URL, {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });
    
        if (response.ok) {
            const { jwt_token } = await response.json();
            return jwt_token;
        }
        return Promise.reject();
    }
}

export async function register({ username, password }) {
    const response = await fetch(APP_USERS_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, password })
    });

    if (response.status === 400) {
        const errors = await response.json();
        return Promise.reject(errors);
    }
    if (response.status === 201) {
        return Promise.resolve();
    }
    return Promise.reject();
}