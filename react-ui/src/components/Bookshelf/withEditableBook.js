import React, { useState, useEffect } from "react";
import { findById, save } from "../../services/bookService";

export const withEditableBook = (Component, bookId) => {
    return props => {
        const [ originalBook, setOriginalBook ] = useState(null);
        const [ book, setBook ] = useState(null);

        useEffect(() => {
            const response = findById(bookId);
            setOriginalBook(response);
            setBook(response);
        }, []);

        const onChangeBook = changes => {
            setBook({ ...book, ...changes });
        }

        const onSaveBook = async () => {
            const response = save(book);
            setOriginalBook(response);
            setBook(response);
        }

        const onResetBook = () => {
            setBook(originalBook);
        }

        return <Component { ...props } 
                    book={book} 
                    onChangeBook={onChangeBook} 
                    onSaveBook={onSaveBook}
                    onResetBook={onResetBook}
                />
    }
}