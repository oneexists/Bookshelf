import { NavLink } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";
import { SplitScreen } from "../layouts/SplitScreen";
import Title from "../Title";
import BookTable from "./BookTable";
import Background from "../Background";
import { withBooks } from "./withBooks";

export default function Bookshelf() {
    const auth = useAuth();
    const BookTableWithLoader = withBooks(BookTable, auth.user.id);

    return (
        <Background>
            <Title text="Bookshelf" />
            
            <SplitScreen leftWeight={1} rightWeight={3}>
                <ul className="nav navbar-nav me-4">
                    <li className="nav-item mb-2"><NavLink to="books/add" className="btn btn-secondary w-100" role="button">Add Book</NavLink></li>
                </ul>
                
                <BookTableWithLoader />
            </SplitScreen>
        </Background>
    );
}