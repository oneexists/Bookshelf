import { printProps } from "./printProps";
import { BookInfo } from "./BookInfo";
import { findAll } from "../../services/bookService";

const BookInfoWrapped = printProps(BookInfo);

export default function Bookshelf() {

    return (
        <main className="container mt-3">
            <BookInfoWrapped book={findAll()} />
        </main>
    );
}