import { useEffect, useState } from "react";
import { findAll } from "../../services/bookService";

export default function Register() {
    const [ books, setBooks ] = useState([]);

    useEffect(() => {
        setBooks(findAll());
    }, []);

    if (books) {
        return (
            <main className="container mt-3">
                <ul>
                    {books.map(book => (
                        <li key={book.id}>{book.title} by {book.author}</li>
                    ))}
                </ul>

                <button className="btn btn-secondary" onClick={() => setBooks([])}>Empty List</button>
            </main>
        );
    }
    return (
        <main className="container mt-3">
            <p>No Books</p>
        </main>
    );
}