import { APP_USERS_URL, BOOKS_URL, TOKEN_KEY } from "../config/bookshelfApi";

export async function findUserBooks({ id }) {
    const response = await fetch(`${APP_USERS_URL}/${id}/books?projection=inlineAuthor`, {
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
    const response = await fetch(BOOKS_URL, {
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

export async function deleteBookById(id) {
    const response = await fetch(`${BOOKS_URL}/${id}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem(TOKEN_KEY)}`
        }
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
    const response = await fetch(`${BOOKS_URL}/${bookId}`, {
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