import BookDetails from "../book/BookDetails";
import SecondaryNavLinkButton from "../../buttons/SecondaryNavLinkButton";
import WarningNavLinkButton from "../../buttons/WarningNavLinkButton";

export default function BookCard({ book }) {
    const { bookId, title, author, language, pages } = book;

    return (
        <div className="col mb-3" key={bookId}>
            <div className="card">
                <div className="card-body">
                    <h5 className="card-title">{title}</h5>
                    <BookDetails author={author} language={language} pages={pages} />
                </div>
                <div className="card-footer d-flex justify-content-center">
                    <SecondaryNavLinkButton url={`/books/${bookId}`} text="View Book" />
                    <WarningNavLinkButton url={`/books/${bookId}/edit`} text="Edit Book" />
                </div>
            </div>
        </div>
    );
}