import { BookLoader } from "./Book/BookLoader";
import { BookInfo } from "./Book/BookInfo";
import { ResourceLoader } from "./ResourceLoader";
import { DataSource } from "./DataSource";
import { findById } from "../../services/bookService";

export default function Bookshelf() {
    return (
        <main className="container mt-3">
            <DataSource getDataFunc={findById(2)} resourceName="book">
                <BookInfo />
            </DataSource>
            <ResourceLoader resourceUrl="http://localhost:8080/api/books/1" resourceName="book">
                <BookInfo />
            </ResourceLoader>
            <BookLoader bookId={2}>
                <BookInfo />
            </BookLoader>
        </main>
    );
}