import { NavLink } from "react-router-dom";
import BookDropdown from "./BookDropdown";

export default function UserMenu() {
    return (
        <>
            <BookDropdown />
            <NavLink to="/quotes" className="nav-link">Quotes</NavLink>
            <NavLink to="/notes" className="nav-link">Notes</NavLink>
        </>
    );
}