import { useResource } from "../../hooks/useResource";
import { AuthorInfo } from "./AuthorInfo";

export const BookInfo = ({ bookId }) => {
    const book = useResource(`http://localhost:8080/api/books/${bookId}`);
    const { title, pages, language, _links } = book || {};
    const { author } = _links || {};
    const { href } = author || {};
    
    return book ? (
        <>
            <h3>{title}</h3>
            <AuthorInfo authorUrl={href} />
            <p>Pages: {pages}</p>
            <p>Language: {language}</p>
        </>
    ) : <p>Loading...</p>;
}