import { BookLoader } from "./Book/BookLoader";
import { BookInfo } from "./Book/BookInfo";

export default function Bookshelf() {
    return (
        <main className="container mt-3">
            <BookLoader bookId={1}>
                <BookInfo />
            </BookLoader>
            <BookLoader bookId={2}>
                <BookInfo />
            </BookLoader>
        </main>
    );
}