const API_URL = process.env.REACT_APP_API_URL;

export const TOKEN_KEY = "bujo-bookshelf";

// AUTH
export const AUTHENTICATE_URL = `${API_URL}/authenticate`;
export const APP_USERS_URL = `${API_URL}/api/appUsers`;
export const REFRESH_URL = `${API_URL}/refresh`;

// AUTHORS
export const AUTHORS_URL = `${API_URL}/api/authors`;

// BOOKS
export const BOOKS_URL = `${API_URL}/api/books`;
export const IN_PROGRESS_BOOKS_URL = `${BOOKS_URL}/inProgress`;

// READING LOGS
export const READING_LOG_URL = `${API_URL}/api/readingLogs`;