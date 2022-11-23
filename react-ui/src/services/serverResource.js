export const serverResource = resourceUrl => async() => {
    const response = await fetch(resourceUrl);
    return response.json();
}