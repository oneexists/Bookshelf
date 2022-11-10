import { useRef, useState } from "react";

export default function Register() {
    const [ name, setName ] = useState("");
    const [ color, setColor ] = useState("#000000");
    const [ message, setMessage ] = useState("");

    const handleSubmit = (evt) => {
        evt.preventDefault();
        setMessage(`Color: ${color} has the name ${name}`);
        
        setName("");
        setColor("#000000");
    };

    return (
        <main className="container mt-3">
            <form onSubmit={handleSubmit}>
                <input 
                    type="text" 
                    value={name} 
                    onChange={(e) => setName(e.target.value)}
                />
                <input 
                    type="color" 
                    value={color} 
                    onChange={(e) => setColor(e.target.value)}
                />
                <button type="submit" className="btn btn-primary">Add Color</button>
            </form>
            {message.length > 0 && (
                <p>{message}</p>
            )}
        </main>
    );
}