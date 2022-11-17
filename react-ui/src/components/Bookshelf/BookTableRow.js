import { Link } from "react-router-dom";
import AuthorTableRow from "./AuthorTableRow";

export default function BookTableRow({ book }) {
    const { id, title, author, language, pages } = book;

    return (
        <tr>
            <td><Link to={`/books/${id}`}>{title}</Link></td>
            <td><AuthorTableRow url={author} /></td>
            <td>{pages}</td>
            <td>{language}</td>
        </tr>
    );
}