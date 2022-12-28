import { useParams } from "react-router-dom";
import { useDataSource } from "../../../hooks/useDataSource";
import { serverResource } from "../../../services/serverResource";
import BookDetails from "../../Bookshelf/book/BookDetails";
import Background from "../../layouts/Background";
import { SplitScreen } from "../../layouts/SplitScreen";
import Title from "../../Title";
import ReadingLogAddForm from "./ReadingLogAddForm";

const API_URL = process.env.REACT_APP_API_URL;

export default function ReadingLogAdd() {    
    const { id } = useParams("id");
    const book = useDataSource(serverResource(`${API_URL}/api/books/${id}?projection=inlineAuthor`));
    const { title, author, pages, language } = book || {};

    return book ? (
        <Background>
            <Title text="Add Reading Log" />
            <SplitScreen leftWeight={1} rightWeight={3}>
                <div id="book-details" className="m-4 p-2">
                    <div className="flex-fill">{title}</div>
                    <BookDetails { ...{author, language, pages} } />
                </div>

                <ReadingLogAddForm />
            </SplitScreen>
        </Background>
    ) : <p>Loading...</p>;
}