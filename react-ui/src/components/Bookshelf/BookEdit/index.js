import { useParams } from "react-router-dom";
import { useDataSource } from "../../../hooks/useDataSource";
import { serverResource } from "../../../services/serverResource";
import Background from "../../layouts/Background";
import Title from "../../Title";
import BookEditForm from "./BookEditForm";
import { withAuthor } from "../book/withAuthor";

const API_URL = process.env.REACT_APP_API_URL;

export default function BookEdit() {
    const { id } = useParams("id");
    const book = useDataSource(serverResource(`${API_URL}/api/books/${id}`));
    const { title, pages, language } = book || {};
    const href = book ? book._links.author.href : {};

    const BookEditFormLoadedAuthor = withAuthor(BookEditForm, href);

    return book && (
        <Background>
            <Title text="Edit Book" />

            <BookEditFormLoadedAuthor { ...{ title, pages, language } } />
        </Background>
    );
}