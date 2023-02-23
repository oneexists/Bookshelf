import { Nav } from "react-bootstrap";
import BookDropdown from "./BookDropdown";

export default function UserMenu() {
    return (
        <>
            <BookDropdown />
            <Nav>
                <Nav.Link to="/quotes">Quotes</Nav.Link>
                <Nav.Link to="/notes">Notes</Nav.Link>
            </Nav>
        </>
    );
}