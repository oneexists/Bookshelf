import { Link } from "react-router-dom";

export default function Login() {
    return (
        <main className="container mt-3">
            <h2 className="d-flex justify-content-center">Bookshelf Login</h2>
            <p>Error Messages</p>
            <form>
                <div className="form-group mb-3">
                    <label htmlFor="username" className="form-label">Username</label>
                    <input type="text" id="username" className="form-control" required />
                </div>

                <div className="form-group mb-3">
                    <label htmlFor="password" className="form-label">Password</label>
                    <input type="password" id="password" className="form-control" required />
                </div>

                <div className="row d-flex justify-content-center">
                    <button type="submit" className="btn btn-primary col-5 me-2">Login</button>
                    <Link to="/" className="btn btn-warning col-5" role="button">Cancel</Link>
                </div>
            </form>
        </main>
    );
}