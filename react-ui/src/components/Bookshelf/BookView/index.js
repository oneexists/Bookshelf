import { useNavigate, useParams } from "react-router-dom";
import styles from "./BookView.module.css";
import { useDataSource } from "../../../hooks/useDataSource";
import { serverResource } from "../../../services/serverResource";
import Background from "../../layouts/Background";
import { SplitScreen } from "../../layouts/SplitScreen";
import Title from "../../Title";
import { deleteBookById } from "../../../services/bookService";
import BookDetails from "../book/BookDetails";
import BookLogs from "../../ReadingLog/BookLogs";
import BookViewNavBar from "./BookViewNavBar";

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
            <BookViewNavBar handleDelete={handleDelete} />

            <SplitScreen leftWeight={1} rightWeight={3}>
                <div id={styles.bookDetails} className="m-4 p-2">
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