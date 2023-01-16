import BookDetails from "../components/BookDetails";
import SecondaryNavLinkButton from "../../../components/buttons/SecondaryNavLinkButton";
import WarningNavLinkButton from "../../../components/buttons/WarningNavLinkButton";
import ButtonBar from "../../../components/layouts/ButtonBar";

export default function BookCard({ book }) {
    const { bookId, title, author, language, pages } = book;

    return (
        <div className="col mb-3" key={bookId}>
            <div className="card">
                <div className="card-body">
                    <h5 className="card-title">{title}</h5>
                    <BookDetails author={author} language={language} pages={pages} />
                </div>
                <div className="card-footer">
                    <ButtonBar>
                        <SecondaryNavLinkButton url={`/books/${bookId}`} text="View Book" marginEnd={2} />
                        <WarningNavLinkButton url={`/books/${bookId}/edit`} text="Edit Book" />
                    </ButtonBar>
                </div>
            </div>
        </div>
    );
}