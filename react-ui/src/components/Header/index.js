import NavBar from "./NavBar";

export default function Header() {
    return (
        <header className="page-header">
            <div className="d-flex bg-dark text-white">
                <div className="col mt-4">
                    <h1 className="d-flex justify-content-center">Bullet Journal Bookshelf</h1>
                </div>
            </div>
            <NavBar />
        </header>
    );
}