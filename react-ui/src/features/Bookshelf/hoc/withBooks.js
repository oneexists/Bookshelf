import React, { useEffect, useState } from "react";
import { useAuth } from "../../../hooks/useAuth";
import { findUserBooks } from "../../../services/bookService";

export const withBooks = (Component, message) => {
    return props => {
        const auth = useAuth();
        const [ books, setBooks ] = useState([]);
    
        useEffect(() => {
            findUserBooks({ id: auth.user.id }).then(b => {
                setBooks(b._embedded.books);
            });
        }, [auth.user.id]);
    
        return books.length > 0 
            ? <Component { ...props } books={books} /> 
            : <p>{message}</p>;
    };
}