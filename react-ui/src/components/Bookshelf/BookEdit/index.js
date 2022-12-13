import { useParams } from "react-router-dom";
import { useDataSource } from "../../../hooks/useDataSource";
import { serverResource } from "../../../services/serverResource";
import Background from "../../Background";
import Title from "../../Title";
import AuthorEdit from "./AuthorEdit";

export default function BookEdit() {
    const { id } = useParams("id");
    const book = useDataSource(serverResource(`http://localhost:8080/api/books/${id}`));
    const { title, pages, language } = book || {};
    const href = book ? book._links.author.href : {};

    return book && (
        <Background>
            <Title text="Edit Book" />

            <AuthorEdit { ...{ title, url: href, language, pages } } />
        </Background>
    );
}