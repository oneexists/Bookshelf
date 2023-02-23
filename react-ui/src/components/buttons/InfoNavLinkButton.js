import { NavLink } from "react-router-dom";

export default function InfoNavLinkButton({ url, text, marginEnd }) {
  return (
    <NavLink
      to={url}
      className={`btn btn-info w-100 me-${marginEnd}`}
      role="button"
    >
      {text}
    </NavLink>
  );
}
