import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";
import { useInput } from "../../hooks/useInput";
import { authenticate } from "../../services/authService";
import ErrorPanel from "../forms/ErrorPanel";
import SubmitPanel from "../forms/SubmitPanel";

export default function Login() {
    const auth = useAuth();
    const usernameRef = useRef();
    const errorRef = useRef();
    const navigate = useNavigate();

    const [ usernameProps, resetUsername ] = useInput("");
    const [ passwordProps, resetPassword ] = useInput("");
    const [errorMsg, setErrorMsg] = useState("");

    useEffect(() => {
        usernameRef.current.focus();
    }, []);

    const handleSubmit = async (evt) => {
        evt.preventDefault();

        const username = usernameProps.value;
        const password = passwordProps.value;

        authenticate({ username, password })
            .then((token) => {
                auth.login(token);
                navigate("/");
                resetUsername();
                resetPassword();
            }).catch((err) => {
                if (err && err.message === "Failed to fetch") {
                    setErrorMsg("Service is unavailable, please try again later");
                } else {
                    setErrorMsg("Login Error");
                }
            });
    };

    return (
        <main className="container mt-3">
            <h2 className="d-flex justify-content-center">Bookshelf Login</h2>

            <p>{(auth.isLoading) ? "Logging in..." : ""}</p>

            <ErrorPanel errorRef={errorRef} errorMsg={errorMsg} />

            <form onSubmit={handleSubmit}>
                <section className="form-group mb-3">
                    <label htmlFor="username" className="form-label">Username:</label>
                    <input 
                        type="text"
                        aria-label="login username"
                        aria-required="true"
                        ref={usernameRef}
                        className="form-control"
                        id="username"
                        required
                        { ...usernameProps }
                    />
                </section>

                <section className="form-group mb-3">
                    <label htmlFor="password" className="form-label">Password:</label>
                    <input
                        type="password"
                        aria-label="login password"
                        aria-required="true"
                        className="form-control"
                        id="password"
                        required
                        { ...passwordProps }
                    />
                </section>

                <SubmitPanel text="Login" />
            </form>
        </main>
    )
}