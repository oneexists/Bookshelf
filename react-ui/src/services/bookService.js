const API_URL = "http://localhost:8080/api";

export async function findUserBooks({ id, token }) {
    const response = await fetch(`${API_URL}/appUsers/${id}/books`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }
    });

    if (response.ok) {
        return response.json();
    }
    return Promise.reject();
}