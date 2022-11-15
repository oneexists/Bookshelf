import { findById } from "../../services/bookService";
import { LargeBookListItem } from "./Book/LargeBookListItem";
import { Modal } from "./Modal";

export default function Bookshelf() {
    return (
        <main className="container mt-3">
            <Modal>
                <LargeBookListItem book={findById(1)} />
            </Modal>
        </main>
    );
}