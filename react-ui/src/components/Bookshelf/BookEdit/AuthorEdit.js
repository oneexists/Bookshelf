import { useDataSource } from "../../../hooks/useDataSource";
import { serverResource } from "../../../services/serverResource";
import BookEditForm from "./BookEditForm";

export default function AuthorEdit({ title, url, language, pages }) {
    const author = useDataSource(serverResource(url));
    const { authorId, name } = author || {};

    return name && (
        <BookEditForm { ...{ title, authorId, name, language, pages }} />
    );
}