import { useEffect, useRef } from "react";
import { useNavigate, Link } from "react-router-dom";

export default function Register() {
    const navigate = useNavigate();
    const usernameRef = useRef(null);

    useEffect(() => {
        usernameRef.current.focus();
    }, []);
    
    const handleSubmit = (evt) => {
        evt.preventDefault();
        navigate("/login", { state: { username: usernameRef.current.value } });
    }

    return (
        <main className="container mt-3">
            <h2 className="d-flex justify-content-center">Bookshelf Registration</h2>
            <p>Error Messages</p>
            <form onSubmit={handleSubmit} autoComplete="off">
                <div className="form-group mb-3">
                    <label htmlFor="username" className="form-label">Username</label>
                    <input type="text" id="username" ref={usernameRef} className="form-control" required />
                </div>

                <div className="form-group mb-3">
                    <label htmlFor="password" className="form-label">Password</label>
                    <input type="password" id="password" className="form-control" required />
                </div>

                <div className="form-group mb-3">
                    <label htmlFor="confirmPassword" className="form-label">Confirm Password</label>
                    <input type="password" id="confirmPassword" className="form-control" required />
                </div>

                <div className="row d-flex justify-content-center">
                    <button type="submit" className="btn btn-primary col-5 me-2">Register</button>
                    <Link to="/" className="btn btn-warning col-5" role="button">Cancel</Link>
                </div>
            </form>
        </main>
    );
}