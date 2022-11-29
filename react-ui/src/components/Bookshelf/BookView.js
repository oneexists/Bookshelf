import { NavLink, useParams } from "react-router-dom";
import { useDataSource } from "../../hooks/useDataSource";
import { serverResource } from "../../services/serverResource";
import Background from "../Background";
import { SplitScreen } from "../layouts/SplitScreen";
import Title from "../Title";
import BookDetail from "./BookDetail";

const API_URL = process.env.REACT_APP_API_URL;

export default function BookView() {
    const { id } = useParams("id");
    const book = useDataSource(serverResource(`${API_URL}/api/books/${id}?projection=inlineAuthor`));
    const { title, author, pages, language } = book || {};

    return book ? (
        <Background>
            <Title text={title} />

            <SplitScreen leftWeight={1} rightWeight={3}>
                <ul className="nav navbar-nav nav-justified mt-3 me-4">
                    <li className="nav-item mb-2"><NavLink to="logs/add" className="btn btn-secondary w-100" role="button">Add Activity</NavLink></li>
                    <li className="nav-item mb-2"><NavLink to="quotes/add" className="btn btn-secondary w-100" role="button">Add Quote</NavLink></li>
                    <li className="nav-item mb-2"><NavLink to="notes/add" className="btn btn-secondary w-100" role="button">Add Note</NavLink></li>
                    <li className="nav-item mb-2"><NavLink to="edit" className="btn btn-warning w-100" role="button">Edit</NavLink></li>
                    <li className="nav-item mb-2"><button className="btn btn-danger w-100">Delete</button></li>
                </ul>
                <>
                    <div className="d-flex p-2 mt-3">
                        <BookDetail { ...{author, language, pages} } />
                    </div>
                    <h4 className="mt-3">Reading Activity</h4>

                    <h4 className="mt-3">Quotes</h4>

                    <h4 className="mt-3">Notes</h4>
                </>
            </SplitScreen>
        </Background>
    ) : <p>Loading...</p>;
}