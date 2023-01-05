import { READING_LOG_URL, TOKEN_KEY } from "../config/bookshelfApi";

export async function createLog({ bookId, start, finish }) {
    const response = await fetch(READING_LOG_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem(TOKEN_KEY)}`
        },
        body: JSON.stringify({ bookId, start, finish })
    });

    if (response.ok) {
        return response.json();
    } else if (response.status === 400) {
        const errors = await response.json();
        return Promise.reject(errors);
    }
    return Promise.reject();
}

export async function editLog({ readingLogId, start, finish }) {
    const response = await fetch(`${READING_LOG_URL}/${readingLogId}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem(TOKEN_KEY)}`
        },
        body: JSON.stringify({ start, finish })
    });

    if (response.ok) {
        return response.json();
    } else if (response.status === 400) {
        const errors = await response.json();
        return Promise.reject(errors);
    }
    return Promise.reject();
}

export async function deleteLogById(id) {
    const response = await fetch(`${READING_LOG_URL}/${id}`, {
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