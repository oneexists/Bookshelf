import jwtDecode from "jwt-decode";
import { createContext, useContext, useState } from "react";

const LOCAL_STORAGE_TOKEN_KEY = "bujo-bookshelf";
const authContext = createContext();

export function ProvideAuth({ children }) {
    const auth = useProvideAuth();
    return <authContext.Provider value={auth}>{children}</authContext.Provider>
}

export const useAuth = () => {
    return useContext(authContext);
};

function useProvideAuth() {
    const [user, setUser] = useState(null);
  
    const login = (token) => {
        localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token);
        const { sub: username, app_user_id: id, authorities: authoritiesString } = jwtDecode(token);
        const roles = authoritiesString.split(",");
    
        const user = {
            id,
            username,
            roles,
            token,
            hasRole(role) {
            return this.roles.includes(role);
            }
        };
        setUser(user);
        return user;
    };
  
    const logout = () => {
        setUser(null);
        localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
    };
  
    const auth = {
        user: user ? { ...user } : null,
        login,
        logout
    };

    return auth;
}