import { NavLink } from "react-router-dom";
import styled from "styled-components";

const TitleLink = styled(NavLink)`
    text-decoration: none;
    &:hover {
        background-color: lightgrey;
    }
    &.active {
        background-color: dimgrey;
        color: ghostwhite;
    }
`;

export default function BookTableRow({ book }) {
    const { bookId, title, author, language, pages } = book;

    return (
        <tr key={bookId}>
            <td><TitleLink to={`/books/${bookId}`}>{title}</TitleLink></td>
            <td>{author.name}</td>
            <td>{pages}</td>
            <td>{language}</td>
        </tr>
    );
}