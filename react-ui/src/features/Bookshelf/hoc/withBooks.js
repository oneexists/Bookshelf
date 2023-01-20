import React, { useEffect, useState } from "react";
import { findUserBooks } from "../../../services/bookService";

export const withBooks = (Component, id, message) => {
    return props => {
        const [ books, setBooks ] = useState([]);
    
        useEffect(() => {
            findUserBooks({ id }).then(b => {
                setBooks(b._embedded.books);
            });
        }, []);
    
        return books.length > 0 
            ? <Component { ...props } books={books} /> 
            : <p>{message}</p>;
    };
}