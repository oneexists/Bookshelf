const API_URL = "http://localhost:8080";
const TOKEN_KEY = "bujo-bookshelf";

export async function authenticate({ username, password }) {
    const response = await fetch(`${API_URL}/authenticate`, {
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
        const response = await fetch(`${API_URL}/refresh`, {
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
    const response = await fetch(`${API_URL}/create_account`, {
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