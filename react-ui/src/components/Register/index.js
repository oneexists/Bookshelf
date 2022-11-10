import { useState } from "react";

export default function Register() {
    const [ checked, setChecked ] = useState(false);

    const createArray = (length) => [
        ...Array(length)
    ];

    function RatingDisplay({ selected = false, onSelect }) {
        return (
            <button 
                className={selected ? "btn btn-danger" : "btn btn-secondary"}
                onClick={onSelect}
            ></button>
        );
    }

    function Rating({ total = 5 }) {
        const[ selected, setSelected ] = useState(0);

        return (
            <p>
                <span className="me-3">
                    {createArray(total).map((n, i) => (
                        <RatingDisplay 
                        key={i} 
                        selected={selected > i} 
                        onSelect={() => setSelected(i + 1)} 
                        />
                    ))}
                </span>
                {selected} of {total}
            </p>
        );
    }

    return (
        <main className="container mt-3">
            <h2 className="d-flex justify-content-center">Bookshelf Registration</h2>
            
            <p>Rating: {<Rating total={10}/>}</p>
            <p>Default Rating: {<Rating />}</p>
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