import { BookLoader } from "./Book/BookLoader";
import { BookInfo } from "./Book/BookInfo";
import { ResourceLoader } from "./ResourceLoader";
import { DataSource } from "./DataSource";
import { findById } from "../../services/bookService";

const getBookData = id => () => {
    return findById(id);
}

const getLocalStorageData = key => () => {
    return localStorage.getItem(key);
}

const Text = ({ message }) => <h3>{message}</h3>;

export default function Bookshelf() {
    return (
        <main className="container mt-3">
            <DataSource getDataFunc={getLocalStorageData("bujo-bookshelf")} resourceName="message">
                <Text />
            </DataSource>
            <DataSource getDataFunc={getBookData(2)} resourceName="book">
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