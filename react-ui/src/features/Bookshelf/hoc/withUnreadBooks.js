import React, { useEffect, useState } from "react";
import { findUnreadUserBooks } from "../../../services/bookService";

export const withUnreadBooks = (Component, message) => {
    return props => {
        const [ books, setBooks ] = useState([]);
    
        useEffect(() => {
            findUnreadUserBooks().then(setBooks);
        }, []);
    
        return books.length > 0 
            ? <Component { ...props } books={books} /> 
            : <p>{message}</p>;
    };
}