import { useState } from "react";
import { useInput } from "../../hooks/useInput";

export default function Register() {
    const [ nameProps, resetName ] = useInput("");
    const [ colorProps, resetColor ] = useInput("#000000");
    const [ message, setMessage ] = useState("");

    const handleSubmit = (evt) => {
        evt.preventDefault();
        setMessage(`Color: ${colorProps.value} has the name ${nameProps.value}`);
        
        resetName();
        resetColor();
    };

    return (
        <main className="container mt-3">
            <form onSubmit={handleSubmit}>
                <input type="text" {...nameProps} />
                <input type="color" {...colorProps} />
                <button type="submit" className="btn btn-primary">Add Color</button>
            </form>
            {message.length > 0 && (
                <p>{message}</p>
            )}
        </main>
    );
}