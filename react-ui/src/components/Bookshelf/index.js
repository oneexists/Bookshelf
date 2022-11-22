import { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";
import { findUserBooks } from "../../services/bookService";
import { SplitScreen } from "../layouts/SplitScreen";
import Background from "../Background";
import Title from "../Title";
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
        <Background>
            <Title text="Bookshelf" />
            
            <SplitScreen leftWeight={1} rightWeight={3}>
                <ul className="nav navbar-nav me-4">
                    <li className="nav-item mb-2"><NavLink to="books/add" className="btn btn-secondary w-100" role="button">Add Book</NavLink></li>
                </ul>
                <section>
                    {books.length > 0 
                        ? <BookTable books={books} />
                        : <p>Add a book to get started!</p>
                    }
                </section>
            </SplitScreen>

            
        </Background>
    );
}