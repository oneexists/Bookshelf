import { NavLink, Outlet } from "react-router-dom";
import { findAll } from "../../services/bookService";
import styled from "styled-components";

const books = findAll();

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

export default function Bookshelf() {
    return (
        <main className="container mt-3">
            <Outlet />
            <table className="table">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Author</th>
                    </tr>
                </thead>
                <tbody>
                    {books.map( b => (
                        <tr key={b.id}>
                            <td><TitleLink to={`${b.id}`} activeclassname="active">{b.title}</TitleLink></td>
                            <td>{b.author}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </main>
    );
}