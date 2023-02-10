export default function ButtonBar({ children }) {
    return (
        <div className="d-flex justify-content-center" data-testid="button-bar">
            {children}
        </div>
    );
}