import { useNavigate, useParams } from "react-router-dom";
import { useDataSource } from "../../../hooks/useDataSource";
import { serverResource } from "../../../services/serverResource";
import Background from "../../layouts/Background";
import { SplitScreen } from "../../layouts/SplitScreen";
import Title from "../../Title";
import { deleteBookById } from "../../../services/bookService";
import BookDetails from "../book/BookDetails";
import BookLogs from "../../ReadingLog/BookLogs";
import SecondaryNavLinkButton from "../../buttons/SecondaryNavLinkButton";
import WarningNavLinkButton from "../../buttons/WarningNavLinkButton";
import DangerButton from "../../buttons/DangerButton";
import ButtonBar from "../../layouts/ButtonBar";

const API_URL = process.env.REACT_APP_API_URL;

export default function BookView() {
    const { id } = useParams("id");
    const navigate = useNavigate();
    const book = useDataSource(serverResource(`${API_URL}/api/books/${id}?projection=inlineAuthor`));
    const { title, author, pages, language } = book || {};

    const handleDelete = () => {
        deleteBookById(id).then(navigate("/"));
    }

    return book ? (
        <Background>
            <Title text={title} />
            <ButtonBar>
                <SecondaryNavLinkButton url="logs/add" text="Add Dates Read" marginEnd={2} />
                <SecondaryNavLinkButton url="quotes/add" text="Add a Quote" marginEnd={2} />
                <SecondaryNavLinkButton url="notes/add" text="Add a Note" marginEnd={2} />
                <WarningNavLinkButton url="edit" text="Edit Book Details" marginEnd={2} />
                <DangerButton text="Delete Book" handleClick={handleDelete} />
            </ButtonBar>

            <SplitScreen leftWeight={1} rightWeight={3}>
                <div id="book-details" className="m-4 p-2">
                    <BookDetails { ...{author, language, pages} } />
                </div>

                <div className="mt-3">
                    <h4>Reading Activity</h4>
                    <BookLogs url={book._links.readingLogs.href} />

                    <h4 className="mt-3">Quotes</h4>

                    <h4 className="mt-3">Notes</h4>
                </div>
            </SplitScreen>
        </Background>
    ) : <p>Loading...</p>;
}