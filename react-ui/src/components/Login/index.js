import { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";
import { authenticate } from "../../services/authService";

export default function Login() {
    const auth = useAuth();
    const usernameRef = useRef();
    const errorRef = useRef();
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errorMsg, setErrorMsg] = useState("");

    useEffect(() => {
        usernameRef.current.focus();
    }, []);

    const handleSubmit = async (evt) => {
        evt.preventDefault();

        authenticate({ username, password })
            .then((token) => {
                auth.login(token);
                navigate("/");
            }).catch((err) => {
                if (err) {
                    setErrorMsg(err.message);
                } else {
                    setErrorMsg("Login Error");
                }
            });
    }

    return (
        <main className="container mt-3">
            <h2 className="d-flex justify-content-center">Bookshelf Login</h2>

            <p>{(auth.isLoading) ? "Logging in..." : ""}</p>

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

                <div className="row d-flex justify-content-center">
                    <button type="submit" className="btn btn-primary col-5 me-2">Login</button>
                    <Link to="/" className="btn btn-warning col-5" role="button">Cancel</Link>
                </div>
            </form>
        </main>
    )
}