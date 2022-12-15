import { useParams } from "react-router-dom";
import { useDataSource } from "../../../hooks/useDataSource";
import { serverResource } from "../../../services/serverResource";
import Background from "../../Background";
import Title from "../../Title";
import BookEditForm from "./BookEditForm";
import { withAuthor } from "./withAuthor";

export default function BookEdit() {
    const { id } = useParams("id");
    const book = useDataSource(serverResource(`http://localhost:8080/api/books/${id}`));
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