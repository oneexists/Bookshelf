import React, { useState, useEffect } from "react";
import { findById } from "../../services/bookService";

export const withBook = (Component, bookId) => {
    return props => {
        const [ book, setBook ] = useState(null);
    
        useEffect(() => {
            const response = findById(bookId);
            setBook(response);
        }, []);
    
        return <Component { ...props } book={book} />;
    }
}