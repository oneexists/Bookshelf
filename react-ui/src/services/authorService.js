import { AUTHORS_URL, TOKEN_KEY } from "../config/bookshelfApi";

export async function searchAuthor(name) {
    const searchResponse = await fetch(`${AUTHORS_URL}/search/name?name=${name}`);

    if (searchResponse.ok) {
        return searchResponse.json();
    }
    return Promise.reject();
}

export async function findById(authorId) {
    const response = await fetch(`${AUTHORS_URL}/${authorId}`);

    if (response.ok) {
        return response.json();
    }
    return Promise.reject();
}

export async function createAuthor(name) {
    const response = await fetch (`${AUTHORS_URL}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem(TOKEN_KEY)}`
        },
        body: JSON.stringify({ name })
    });

    if (response.ok) {
        return response.json();
    } else if (response.status === 400) {
        const errors = await response.json();
        return Promise.reject(errors);
    }
    return Promise.reject();
}

export async function updateAuthor(author) {
    const response = await fetch(`${AUTHORS_URL}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem(TOKEN_KEY)}`
        },
        body: JSON.stringify(author)
    });

    if (response.ok) {
        return response.json();
    } else if (response.status === 400) {
        const errors = await response.json();
        return Promise.reject(errors);
    }
    return Promise.reject();
}