import { NavLink } from "react-router-dom";

export default function SecondaryNavLinkButton({ url, text }) {
    return <NavLink to={url} className="btn btn-secondary w-100" role="button">{text}</NavLink>;
}