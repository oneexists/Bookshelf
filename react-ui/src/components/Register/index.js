import { useTrees } from "../../App";

export default function Register() {
    const { trees } = useTrees();

    return (
        <main className="container mt-3">
            <div>
                <h1>Types of Trees</h1>
                <ul>
                    {trees.map((t) => (
                        <li key={t.id}>{t.type}</li>
                    ))}
                </ul>
            </div>
        </main>
    );
}