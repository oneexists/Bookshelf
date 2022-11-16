import React, { useState, useEffect } from "react"
import { findById } from "../../../services/bookService";
export const BookLoader = ({ bookId, children }) => {
    const [ book, setBook ] = useState(null);

    useEffect(() => {
        const response = findById(bookId);
        setBook(response);
    }, [bookId]);

    return (
        <>
            {React.Children.map(children, child => {
                if (React.isValidElement(child)) {
                    return React.cloneElement(child, { book });
                }
                return child;
            })}
        </>
    );
}