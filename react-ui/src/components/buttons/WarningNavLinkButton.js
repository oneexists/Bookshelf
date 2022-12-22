import { NavLink } from "react-router-dom";

export default function WarningNavLinkButton({ url, text, marginEnd }) {
    return <NavLink to={url} className={`btn btn-warning w-100 me-${marginEnd}`} role="button">{text}</NavLink>;
}