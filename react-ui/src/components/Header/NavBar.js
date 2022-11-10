import { NavLink } from "react-router-dom";

export default function NavBar() {
    return (
        <nav className="navbar navbar-expand-md navbar-dark bg-dark">
            <div className="container-fluid">
                <div className="navbar-header">
                    <NavLink to="/" className="navbar-brand">Home</NavLink>
                </div>
                <ul className="nav navbar-nav">
                    <li className="nav-item"><NavLink to="/login" className="nav-link">Login</NavLink></li>
                    <li className="nav-item"><NavLink to="/register" className="nav-link">Register</NavLink></li>
                </ul>
            </div>
        </nav>
    );
}