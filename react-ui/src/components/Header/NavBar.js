import { Container, Nav, Navbar } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";
import LoginNav from "./LoginNav";
import UserMenu from "./UserMenu";

export default function NavBar() {
    const auth = useAuth();
    const navigation = useNavigate();

    const logout = () => {
        auth.logout();
        navigation("/");

    }

    return (
        <Navbar bg="dark" variant="dark">
            <Container fluid>
                <Nav>
                    <Navbar.Brand href="/">
                        <img height="32" src={process.env.PUBLIC_URL + "/book-icon.png"} alt="logo" />
                        Bookshelf
                    </Navbar.Brand>
                    {(auth.user) && 
                        <UserMenu />
                    }
                </Nav>
                {(auth.user)
                ?
                    <Nav>
                        <Nav.Link to="/" onClick={logout}>Logout</Nav.Link>
                    </Nav>
                :
                    <LoginNav />
                }
            </Container>
        </Navbar>
    );
}