import React, { useEffect, useState } from "react";
import { findInProgressUserBooks } from "../../../services/bookService";

export const withInProgressBooks = (Component, message) => {
    return props => {
        const [ books, setBooks ] = useState([]);
    
        useEffect(() => {
            findInProgressUserBooks().then(setBooks);
        }, []);
    
        return books.length > 0 
            ? <Component { ...props } books={books} /> 
            : <p>{message}</p>;
    };
}