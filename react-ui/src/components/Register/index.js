import { useEffect, useState } from "react";

export default function Register() {
    const [ name, setName ] = useState("");
    const [ submittedName, setSubmittedName ] = useState("");
    const [ isAdmin, setIsAdmin ] = useState(false);
    const [ msg, setMsg ] = useState("");

    useEffect(() => {
        document.title = `Hello ${submittedName}`;
    }, [submittedName]);

    useEffect(() => {
        isAdmin ? setMsg("User is an Admin") : setMsg("");
    }, [isAdmin]);

    return (
        <main className="container mt-3">
            <h2 className="d-flex justify-content-center">Bookshelf Registration</h2>
            <p className={(isAdmin && submittedName.length > 0) ? "alert alert-danger" : "offscreen"}>
                    {msg}
            </p>
            <div className="form-group mb-3">
                <label htmlFor="name" className="form-label">Name:</label>
                <input 
                    type="text"
                    aria-label="name"
                    aria-required="true"
                    className="form-control"
                    id="name"
                    value={name}
                    onChange={(evt) => setName(evt.target.value)}
                    required
                />
            </div>
            <div className="form-group mb-3">
                <button className="btn btn-secondary" onClick={() => setIsAdmin((isAdmin) => !isAdmin)}>Admin</button>
            </div>

            <button type="submit" className="btn btn-success" onClick={() => setSubmittedName(name)}>Submit</button>
        </main>
    );
}