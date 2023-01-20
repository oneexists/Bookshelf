import { NavLink } from "react-router-dom";

export default function BookDropdown() {
    return (
        <div className="dropdown">
            <NavLink to="#" className="nav-link dropdown-toggle" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">Books</NavLink>
            <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <li><a href="/" className="dropdown-item">All</a></li>
                <li><a href="/books/unfinished" className="dropdown-item">Unread</a></li>
                <li><a href="/books/in-progress" className="dropdown-item">In Progress</a></li>
                <li><a href="/books/finished" className="dropdown-item">Finished</a></li>
            </ul>
        </div>
    );
}