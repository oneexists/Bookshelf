import { NavLink } from "react-router-dom";

export default function WarningNavLinkButton({ url, text }) {
    return <NavLink to={url} className="btn btn-warning w-100" role="button">{text}</NavLink>;
}