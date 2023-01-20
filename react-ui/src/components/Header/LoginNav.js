import { NavLink } from "react-router-dom";

export default function LoginNav() {
    return (
        <ul className="nav navbar-nav">
            <li className="nav-item"><NavLink to="/login" className="nav-link">Login</NavLink></li>
            <li className="nav-item"><NavLink to="/register" className="nav-link">Register</NavLink></li>
        </ul>
    );
}