import { NavLink } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";

export default function NavBar() {
    const auth = useAuth();

    return (
        <nav className="navbar navbar-expand-md navbar-dark bg-dark">
            <div className="container-fluid">
                <div className="navbar-header">
                    <div className="nav navbar-nav">
                        <NavLink to="/" className="navbar-brand"><img height="32" src={process.env.PUBLIC_URL + "/book-icon.png"} alt="logo" />Bookshelf</NavLink>
                        {(auth.user) &&
                        <>
                            <NavLink to="/quotes" className="nav-link">Quotes</NavLink>
                            <NavLink to="/notes" className="nav-link">Notes</NavLink>
                        </>
                        }
                    </div>
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