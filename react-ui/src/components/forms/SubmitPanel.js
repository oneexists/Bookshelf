import { Link } from "react-router-dom";

export default function SubmitPanel({ text = "Submit", returnLink = "/" }) {
    return (
    <div className="row d-flex justify-content-center">
            <button type="submit" className="btn btn-primary col-5 me-2">{text}</button>
            <Link to={returnLink} className="btn btn-warning col-5" role="button">Cancel</Link>
        </div>
    );
}