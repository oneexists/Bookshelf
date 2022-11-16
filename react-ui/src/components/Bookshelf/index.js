import { ControlledForm } from "./ControlledForm";
import { UncontrolledForm } from "./UncontrolledForm";

export default function Bookshelf() {
    return (
        <main className="container mt-3">
            <h2>Uncontrolled Form</h2>
            <UncontrolledForm />
            <h2>Controlled Form</h2>
            <ControlledForm />
        </main>
    );
}