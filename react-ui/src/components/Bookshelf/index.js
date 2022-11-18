import { BookInfo } from "./BookInfo";

export default function Bookshelf() {

    return (
        <main className="container mt-3">
            <BookInfo bookId={1} />
            <BookInfo bookId={3} />
        </main>
    );
}