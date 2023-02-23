import { Nav, Navbar, NavDropdown } from "react-bootstrap";

export default function BookDropdown() {
    return (
        <Navbar.Collapse id="navbar-book-dropdown">
            <Nav>
                <NavDropdown
                    id="nav-book-dropdown"
                    title="Books"
                    menuVariant="dark"
                >
                    <NavDropdown.Item href="/">All</NavDropdown.Item>
                    <NavDropdown.Item href="/books/unread">Unread</NavDropdown.Item>
                    <NavDropdown.Item href="/books/in-progress">In Progress</NavDropdown.Item>
                    <NavDropdown.Item href="/books/finished">Finished</NavDropdown.Item>
                </NavDropdown>
            </Nav>
        </Navbar.Collapse>
    );
}