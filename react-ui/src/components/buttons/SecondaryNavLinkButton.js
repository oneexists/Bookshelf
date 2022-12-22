import { NavLink } from "react-router-dom";

export default function SecondaryNavLinkButton({ url, text, marginEnd }) {
    return <NavLink to={url} className={`btn btn-secondary w-100 me-${marginEnd}`} role="button">{text}</NavLink>;
}