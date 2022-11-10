import { useRef, useState } from "react";

export default function Register() {
    const name = useRef();
    const color = useRef();
    const [ message, setMessage ] = useState("");

    const handleSubmit = (evt) => {
        evt.preventDefault();
        const nameVal = name.current.value;
        const colorVal = color.current.value;
        setMessage(`Color: ${colorVal} has the name ${nameVal}`);
    };

    return (
        <main className="container mt-3">
            <form onSubmit={handleSubmit}>
                <input type="text" ref={name} />
                <input type="color" ref={color} />
                <button type="submit" className="btn btn-primary">Add Color</button>
            </form>
            {message.length > 0 && (
                <p>{message}</p>
            )}
        </main>
    );
}