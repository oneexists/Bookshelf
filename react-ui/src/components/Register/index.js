import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";
import { useInput } from "../../hooks/useInput";
import { register } from "../../services/authService";
import ErrorPanel from "../forms/ErrorPanel";
import SubmitPanel from "../forms/SubmitPanel";

export default function Register() {
    const auth = useAuth();
    const usernameRef = useRef();
    const errorRef = useRef();
    const navigate = useNavigate();

    const [ usernameProps, resetUsername ] = useInput("");
    const [ passwordProps, resetPassword ] = useInput("");
    const [ confirmPasswordProps, resetConfirmPassword ] = useInput("");
    const [errorMsg, setErrorMsg] = useState([]);

    useEffect(() => {
        usernameRef.current.focus();
    }, []);

    const handleSubmit = async (evt) => {
        evt.preventDefault();

        const username = usernameProps.value;
        const password = passwordProps.value;
        const confirmPassword = confirmPasswordProps.value;

        if (password === confirmPassword) {
            register({ username, password })
                .then(() => {
                    navigate("/login");
                    resetUsername();
                    resetPassword();
                    resetConfirmPassword();
                })
                .catch((err) => {
                    if (err && err.message === "Failed to fetch") {
                        setErrorMsg(["Service is unavailable, please try again later"]);
                    } else if (err) {
                        setErrorMsg(err);
                    } else {
                        setErrorMsg(["An error occurred during registration."]);
                    }
                });
        } else {
            setErrorMsg(["Passwords must match."]);
        }
    };

    return (
        <main className="container mt-3">
            <h2 className="d-flex justify-content-center">Bookshelf Registration</h2>

            <p>{(auth.isLoading) ? "Creating account..." : ""}</p>

            <ErrorPanel errorRef={errorRef} errorMsg={errorMsg} />

            <form onSubmit={handleSubmit}>
                <section className="form-group mb-3">
                    <label htmlFor="username" className="form-label">Username:</label>
                    <input 
                        type="text"
                        aria-label="register username"
                        aria-required="true"
                        ref={usernameRef}
                        className="form-control"
                        id="username"
                        placeholder="Must be least 3 characters"
                        required
                        { ...usernameProps }
                    />
                </section>

                <section className="form-group mb-3">
                    <label htmlFor="password" className="form-label">Password:</label>
                    <input
                        type="password"
                        aria-label="register password"
                        aria-required="true"
                        className="form-control"
                        id="password"
                        placeholder="Must be at least 8 characters with a letter, a number, and a special character"
                        required
                        { ...passwordProps }
                    />
                </section>

                <section className="form-group mb-3">
                    <label htmlFor="confirmPassword" className="form-label">Confirm Password:</label>
                    <input
                        type="password"
                        aria-label="register confirm password"
                        aria-required="true"
                        className="form-control"
                        id="confirmPassword"
                        required
                        { ...confirmPasswordProps }
                    />
                </section>

                <SubmitPanel text="Register" />
            </form>
        </main>
    );
}