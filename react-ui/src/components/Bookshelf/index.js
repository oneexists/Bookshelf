import { BookLoader } from "./Book/BookLoader";
import { BookInfo } from "./Book/BookInfo";
import { ResourceLoader } from "./ResourceLoader";

export default function Bookshelf() {
    return (
        <main className="container mt-3">
            <ResourceLoader resourceUrl="http://localhost:8080/api/books/1" resourceName="book">
                <BookInfo />
            </ResourceLoader>
            <BookLoader bookId={2}>
                <BookInfo />
            </BookLoader>
        </main>
    );
}