import { BookInfo } from "./BookInfo";
import { withBook } from "./withBook";
import { BookInfoForm } from "./BookInfoForm";

const BookInfoWithLoader = withBook(BookInfo, 2);

export default function Bookshelf() {

    return (
        <main className="container mt-3">
            <BookInfoForm />
        </main>
    );
}