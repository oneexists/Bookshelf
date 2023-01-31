import React, { useEffect, useState } from "react";
import { findFinishedUserBooks } from "../../../services/bookService";

export const withFinishedBooks = (Component, message) => {
    return props => {
        const [ books, setBooks ] = useState([]);
    
        useEffect(() => {
            findFinishedUserBooks().then(setBooks);
        }, []);
    
        return books.length > 0 
            ? <Component { ...props } books={books} /> 
            : <p>{message}</p>;
    };
}