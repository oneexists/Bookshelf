export default function ErrorPanel({ errorRef, errorMsg }) {
    return (
        <p 
            ref={errorRef}
            className={errorMsg ? "alert alert-danger" : "offscreen"}
            role="alert"
            aria-live="assertive"
        >
            {errorMsg}
        </p>
    );
}