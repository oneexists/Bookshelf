import { useEffect, useState } from "react";

export default function Register() {
    const [ name, setName ] = useState("");
    const [ submittedName, setSubmittedName ] = useState("");

    useEffect(() => {
        document.title = `Hello ${submittedName}`;
    });

    return (
        <main className="container mt-3">
            <h2 className="d-flex justify-content-center">Bookshelf Registration</h2>

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

            <button type="submit" className="btn btn-success" onClick={() => setSubmittedName(name)}>Submit</button>
        </main>
    );
}