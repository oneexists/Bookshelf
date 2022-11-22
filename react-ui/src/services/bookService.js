const API_URL = "http://localhost:8080/api";

export async function findUserBooks({ id }) {
    const response = await fetch(`${API_URL}/appUsers/${id}/books`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("bujo-bookshelf")}`
        }
    });

    if (response.ok) {
        return response.json();
    }
    return Promise.reject();
}

export async function createBook({ title, pages, language, authorId, id }) {
    const user = `/users/${id}`;
    const author = `/authors/${authorId}`;

    const response = await fetch(`${API_URL}/books`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("bujo-bookshelf")}`
        },
        body: JSON.stringify({title, pages, language, author, user})
    });

    if (response.ok) {
        return Promise.resolve();
    } else if (response.status === 400) {
        const errors = await response.json();
        return Promise.reject(errors);
    }
    return Promise.reject();
}