import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import ErrorPanel from "../../components/forms/ErrorPanel";
import SectionLabel from "../../components/forms/SectionLabel";
import SubmitPanel from "../../components/forms/SubmitPanel";
import { useInput } from "../../hooks/useInput";
import { register } from "../../services/authService";

export default function RegisterForm() {
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
        <>
            <ErrorPanel errorRef={errorRef} errorMsg={errorMsg} />

            <form onSubmit={handleSubmit}>
                <SectionLabel id="username" text="Username:">
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
                </SectionLabel>

                <SectionLabel id="password" text="Password:">
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
                </SectionLabel>

                <SectionLabel id="confirmPassword" text="Confirm Password:">
                    <input
                        type="password"
                        aria-label="register confirm password"
                        aria-required="true"
                        className="form-control"
                        id="confirmPassword"
                        required
                        { ...confirmPasswordProps }
                    />
                </SectionLabel>

                <SubmitPanel text="Register" />
            </form>
        </>
    )
}