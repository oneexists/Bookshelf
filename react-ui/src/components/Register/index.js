import { useReducer } from "react";

export default function Register() {
    const [ number, setNumber ] =useReducer(
        (number, newNumber) => number + newNumber,
        0
    );

    return (
        <main className="container mt-3">
            <h2 onClick={() => setNumber(1)}>{number}</h2>
        </main>
    );
}