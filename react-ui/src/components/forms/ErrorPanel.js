export default function ErrorPanel({ errorRef, errorMsg }) {
    return (
        <ul 
            ref={errorRef}
            className={errorMsg.length !== 0 ? "alert alert-danger" : "offscreen"}
            role="alert"
            aria-live="assertive"
        >
            {errorMsg.map(m => <li key={m}>{m}</li>)}
        </ul>
    );
}