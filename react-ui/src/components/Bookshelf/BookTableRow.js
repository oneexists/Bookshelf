import { Link } from "react-router-dom";
import AuthorName from "./AuthorName";

export default function BookTableRow({ book }) {
    const { bookId, title, author, language, pages } = book;

    return (
        <tr>
            <td><Link to={`/books/${bookId}`}>{title}</Link></td>
            <td><AuthorName url={author} /></td>
            <td>{pages}</td>
            <td>{language}</td>
        </tr>
    );
}