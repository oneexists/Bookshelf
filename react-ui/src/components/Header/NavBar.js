import { NavLink } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";

export default function NavBar() {
    const auth = useAuth();

    return (
        <nav className="navbar navbar-expand-md navbar-dark bg-dark">
            <div className="container-fluid">
                <div className="navbar-header">
                    <NavLink to="/" className="navbar-brand"><img height="32" src="./book-icon.png" alt="logo" />Bookshelf</NavLink>
                </div>
                    {(auth.user)
                    ?
                        <ul className="nav navbar-nav">
                            <li className="nav-item"><NavLink to="/" className="nav-link" onClick={auth.logout}>Logout</NavLink></li>
                        </ul>
                    :
                        <ul className="nav navbar-nav">
                            <li className="nav-item"><NavLink to="/login" className="nav-link">Login</NavLink></li>
                            <li className="nav-item"><NavLink to="/register" className="nav-link">Register</NavLink></li>
                        </ul>
                    }
            </div>
        </nav>
    );
}