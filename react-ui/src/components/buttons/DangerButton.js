export default function DangerButton({ text, marginEnd, handleClick }) {
    return <button type="button" className={`btn btn-danger w-100 me-${marginEnd}`} onClick={handleClick}>{text}</button>;
}