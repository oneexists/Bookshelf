import { NavLink } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";
import LoginNav from "./LoginNav";
import UserMenu from "./UserMenu";

export default function NavBar() {
    const auth = useAuth();

    return (
        <nav className="navbar navbar-expand-md navbar-dark bg-dark">
            <div className="container-fluid">
                <div className="navbar-header">
                    <div className="nav navbar-nav">
                        <NavLink to="/" className="navbar-brand"><img height="32" src={process.env.PUBLIC_URL + "/book-icon.png"} alt="logo" />Bookshelf</NavLink>
                        {(auth.user) && 
                            <UserMenu />
                        }
                    </div>
                </div>
                    {(auth.user)
                    ?
                        <ul className="nav navbar-nav">
                            <li className="nav-item"><NavLink to="/" className="nav-link" onClick={auth.logout}>Logout</NavLink></li>
                        </ul>
                    :
                        <LoginNav />
                    }
            </div>
        </nav>
    );
}