import { useEffect, useState } from "react";

export default function AuthorTableRow({ url }) {
    const [ author, setAuthor ] = useState("");

    useEffect(() => {
        fetch(`${url}`)
            .then((r) => r.json())
            .then((d) => setAuthor(d.name));
    }, [url]);

    return <>{author}</>;
}