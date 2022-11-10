import { useParams } from "react-router-dom";
import { findById } from "../../services/bookService";


export default function Book() {
    const { id } = useParams();
    const book = findById(Number(id));

    return (
        <main className="container mt-3">
            <h2>{book.title}</h2>
            <p>by {book.author}</p>
            <p>Pages: {book.pages}</p>
            <p>Language: {book.language}</p>
        </main>
    );
}