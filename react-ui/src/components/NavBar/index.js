import { Link } from "react-router-dom";

export default function NavBar() {
    return (
        <nav className="navbar navbar-expand-md navbar-dark bg-dark">
            <div className="container-fluid">
                <div className="navbar-header">
                    <Link to="/" className="navbar-brand">Home</Link>
                </div>
                <ul className="nav navbar-nav">
                    <li className="nav-item"><Link to="/login" className="nav-link">Login</Link></li>
                    <li className="nav-item"><Link to="/register" className="nav-link">Register</Link></li>
                </ul>
            </div>
        </nav>
    );
}