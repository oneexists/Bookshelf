import { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";
import { findUserBooks } from "../../services/bookService";
import { SplitScreen } from "../layouts/SplitScreen";
import BookTable from "./BookTable";

export default function Bookshelf() {
    const auth = useAuth();
    const id = auth.user.id;
    const token = auth.user.token;
    const [ books, setBooks ] = useState([]);

    useEffect(() => {
        findUserBooks({ id, token }).then(b => {
            let newBooks = b._embedded.books;
            newBooks.map(nb => nb.author = nb._links.author.href);
            setBooks(newBooks);
        });
    }, [id, token]);

    return (
        <main className="container mt-3">
            <h2 className="d-flex justify-content-center">Bookshelf</h2>
            <SplitScreen leftWeight={1} rightWeight={3}>
                <ul className="nav navbar-nav">
                    <li className="nav-item"><NavLink to="add_book" className="btn btn-secondary" role="button">Add Book</NavLink></li>
                </ul>
                <section>
                    {books.length > 0 
                        ? <BookTable books={books} />
                        : <p>Add a book to get started!</p>
                    }
                </section>
            </SplitScreen>

            
        </main>
    );
}