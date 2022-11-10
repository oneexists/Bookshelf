import { useState } from "react";

export default function Register() {
    const [ checked, setChecked ] = useState(false);

    return (
        <main className="container mt-3">
            <h2 className="d-flex justify-content-center">Bookshelf Registration</h2>
            
            <form>
                <div className="form-check mb-3">
                    <input 
                        type="checkbox" 
                        className="form-check-input" 
                        name="checked"
                        value={checked} 
                        onChange={() => setChecked((checked) => !checked)} 
                    />
                    <label htmlFor="checked" className="form-check-label">{checked ? "checked" : "not checked"}</label>
                </div>
            </form>
        </main>
    );
}