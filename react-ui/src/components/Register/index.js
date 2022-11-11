import { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";

export default function Register() {
    const auth = useAuth();
    const usernameRef = useRef();
    const errorRef = useRef();
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [errorMsg, setErrorMsg] = useState("");

    useEffect(() => {
        usernameRef.current.focus();
    }, []);

    const handleSubmit = async (evt) => {
        evt.preventDefault();

        if (password === confirmPassword) {
            auth.Register(username, password, confirmPassword);

            if (auth.user) {
                navigate("/");
            }
        } else {
            setErrorMsg("Passwords must match.");
        }
    };

    return (
        <main className="container mt-3">
            <h2 className="d-flex justify-content-center">Bookshelf Registration</h2>

            <p>{(auth.isLoading) ? "Creating account..." : ""}</p>

            <p
                ref={errorRef}
                className={errorMsg ? "alert alert-danger" : "offscreen"}
                role="alert"
                aria-live="assertive">
                    {errorMsg}
            </p>

            <form onSubmit={handleSubmit}>
                <section className="form-group mb-3">
                    <label htmlFor="username" className="form-label">Username:</label>
                    <input 
                        type="text"
                        aria-label="username"
                        aria-required="true"
                        ref={usernameRef}
                        className="form-control"
                        id="username"
                        onChange={(evt) => setUsername(evt.target.value)}
                        required
                    />
                </section>

                <section className="form-group mb-3">
                    <label htmlFor="password" className="form-label">Password:</label>
                    <input
                        type="password"
                        aria-label="password"
                        aria-required="true"
                        className="form-control"
                        id="password"
                        onChange={(evt) => setPassword(evt.target.value)}
                        required
                    />
                </section>

                <section className="form-group mb-3">
                    <label htmlFor="confirmPassword" className="form-label">Confirm Password:</label>
                    <input
                        type="password"
                        aria-label="athlete confirm password"
                        aria-required="true"
                        className="form-control"
                        id="confirmPassword"
                        onChange={(evt) => setConfirmPassword(evt.target.value)}
                        required
                    />
                </section>

                <div className="row d-flex justify-content-center">
                    <button type="submit" className="btn btn-primary col-5 me-2">Register</button>
                    <Link to="/" className="btn btn-warning col-5" role="button">Cancel</Link>
                </div>
            </form>
        </main>
    );
}