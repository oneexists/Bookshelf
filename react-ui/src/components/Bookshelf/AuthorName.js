import { useDataSource } from "../../hooks/useDataSource";

const serverResource = resourceUrl => async() => {
    const response = await fetch(resourceUrl);
    return response.json();
}

export default function AuthorName({ url }) {
    const author = useDataSource(serverResource(url));
    const { name } = author || {};

    return <>{name}</>;
}