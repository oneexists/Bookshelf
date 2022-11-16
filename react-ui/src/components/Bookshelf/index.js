import { useState } from "react";
import { ControlledModal } from "./ControlledModal";

export default function Bookshelf() {
    const [ shouldShowModal, setShouldShowModal ] = useState(false);

    return (
        <main className="container mt-3">
            <button onClick={() => setShouldShowModal(!shouldShowModal)}>
                {shouldShowModal ? "Hide Modal" : "Show Modal"}
            </button>
            <ControlledModal 
                shouldShow={shouldShowModal} 
                onRequestClose={() => setShouldShowModal(false)} 
            >
                <h1>Controlled Modal</h1>
            </ControlledModal>
        </main>
    );
}