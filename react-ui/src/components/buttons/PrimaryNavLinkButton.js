import { NavLink } from "react-router-dom";

export default function PrimaryNavLinkButton({ url, text, marginEnd }) {
  return (
    <NavLink
      to={url}
      className={`btn btn-primary w-100 me-${marginEnd}`}
      role="button"
    >
      {text}
    </NavLink>
  );
}
