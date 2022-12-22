const API_URL = process.env.REACT_APP_API_URL;

const READING_LOG_URL = `${API_URL}/api/readingLogs`;
const TOKEN_KEY = "bujo-bookshelf";

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