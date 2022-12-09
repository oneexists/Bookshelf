const API_URL = process.env.REACT_APP_API_URL;
const TOKEN_KEY = "bujo-bookshelf";

export async function findUserBooks({ id }) {
    const response = await fetch(`${API_URL}/api/appUsers/${id}/books?projection=inlineAuthor`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem(TOKEN_KEY)}`
        }
    });

    if (response.ok) {
        return response.json();
    }
    return Promise.reject();
}

export async function createBook({ title, pages, language, author, appUserId }) {
    const response = await fetch(`${API_URL}/api/books`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem(TOKEN_KEY)}`
        },
        body: JSON.stringify({ bookId: 0, title, pages, language, author, appUserId })
    });

    if (response.ok) {
        return Promise.resolve();
    } else if (response.status === 400) {
        const errors = await response.json();
        return Promise.reject(errors);
    }
    return Promise.reject();
}

export async function updateBook({ bookId, title, pages, language, author, appUserId }) {
    const response = await fetch(`${API_URL}/api/books/${bookId}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem(TOKEN_KEY)}`
        },
        body: JSON.stringify({ bookId, title, pages, language, author, appUserId })
    });

    if (response.ok) {
        return Promise.resolve();
    } else if (response.status === 400) {
        const errors = await response.json();
        return Promise.reject(errors);
    }
    return Promise.reject();
}