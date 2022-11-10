import { useReducer } from "react";

const initialState = {
    message: "hi"
};

function reducer(state, action) {
    switch(action.type) {
        case "yell":
            return {
                message: `HEY! I JUST SAID ${state.message}`
            };
        case "whisper":
            return {
                message: "excuse me..."
            };
        default:
            return {
                message: "hello"
            };
    }
}

export default function Register() {
    const [ state, dispatch ] = useReducer(
        reducer,
        initialState
    );

    return (
        <main className="container mt-3">
            <p>Message: {state.message}</p>
            <button 
                className="btn btn-secondary me-2" 
                onClick={() => dispatch({ type: "yell" })}
            >
                YELL
            </button>
            <button 
                className="btn btn-secondary" 
                onClick={() => dispatch({ type: "whisper" })}
            >
                whisper
            </button>
        </main>
    );
}