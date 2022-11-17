import { BookInfo } from "./BookInfo";
import { withBook } from "./withBook";

const BookInfoWithLoader = withBook(BookInfo, 2);

export default function Bookshelf() {

    return (
        <main className="container mt-3">
            <BookInfoWithLoader />
        </main>
    );
}