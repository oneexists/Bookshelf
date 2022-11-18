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
                <ul className="nav navbar-nav nav-justified mt-3 me-4">
                    <li className="nav-item mb-2"><NavLink to={`books/${id}/logs/add`} className="btn btn-secondary w-100" role="button">Add Activity</NavLink></li>
                    <li className="nav-item mb-2"><NavLink to={`books/${id}/quotes/add`} className="btn btn-secondary w-100" role="button">Add Quote</NavLink></li>
                    <li className="nav-item mb-2"><NavLink to={`books/${id}/notes/add`} className="btn btn-secondary w-100" role="button">Add Note</NavLink></li>
                    <li className="nav-item mb-2"><NavLink to={`books/edit/${id}`} className="btn btn-warning w-100" role="button">Edit</NavLink></li>
                    <li className="nav-item mb-2"><button className="btn btn-danger w-100">Delete</button></li>
                </ul>
                <>
                    <div className="bg-light d-flex p-2 mt-3">
                        <BookDetail { ...{authorName, language, pages} } />
                    </div>
                    <h4 className="mt-3">Reading Activity</h4>

                    <h4 className="mt-3">Quotes</h4>

                    <h4 className="mt-3">Notes</h4>
                </>
            </SplitScreen>
        </main>
    ) : <p>Loading...</p>;
}