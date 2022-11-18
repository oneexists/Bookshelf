import { NavLink, useParams } from "react-router-dom";
import { useDataSource } from "../../hooks/useDataSource";
import { SplitScreen } from "../layouts/SplitScreen";
import AuthorName from "./AuthorName";
import BookDetail from "./BookDetail";

const serverResource = resourceUrl => async() => {
    const response = await fetch(resourceUrl);
    return response.json();
}

export default function BookView() {
    const { id } = useParams("id");
    const book = useDataSource(serverResource(`http://localhost:8080/api/books/${id}`));
    const { title, pages, language, _links } = book || {};
    const { author } = _links || {};
    const { href } = author || {};
    const authorName = <AuthorName url={href} />;

    return book ? (
        <main className="container mt-3">
            <h2 className="d-flex justify-content-center">{title}</h2>
            <SplitScreen leftWeight={1} rightWeight={3}>
                <ul className="nav navbar-nav">
                    <li className="nav-item mb-2"><NavLink to={`books/edit/${id}`} className="btn btn-secondary" role="button">Edit</NavLink></li>
                    <li className="nav-item mb-2"><button className="btn btn-danger">Delete</button></li>
                </ul>
                <BookDetail { ...{authorName, language, pages} } />
            </SplitScreen>
        </main>
    ) : <p>Loading...</p>;
}