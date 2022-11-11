import { createContext, useContext, useState } from "react";

const LOCAL_STORAGE_TOKEN_KEY = "bujo-bookshelf";
const API_URL = "http://localhost:8080";

const authContext = createContext();

export function ProvideAuth({ children }) {
    const auth = useProvideAuth();
    return <authContext.Provider value={auth}>{children}</authContext.Provider>
}

export const useAuth = () => {
    return useContext(authContext);
};

function useProvideAuth() {
    const [ user, setUser ] = useState(null);
    const [ errors, setErrors ] = useState([]);
    const [ isLoading, setIsLoading ] = useState(false);
    
    const login = async (username, password) => {
        return "login";
    }

    const Register = async (username, password) => {
        setErrors([]);
        setIsLoading(true);

        fetch(`${API_URL}/create_account`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, password })
        }).then((r) => {
            setIsLoading(false);
            if (r.ok) {
                r.json().then((user) => setUser(user));
            } else {
                r.json().then((err) => setErrors(err.errors));
            }
        });
    }

    const logout = () => {
        setUser(null);
        localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
    }

    return {
        user, 
        errors,
        isLoading,
        login,
        Register,
        logout
    };
}