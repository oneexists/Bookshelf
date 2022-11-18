import { useResource } from "../../hooks/useResource";

export const AuthorInfo = ({ authorUrl }) => {
    const author = useResource(authorUrl);
    const { name } = author || {};

    return author ? (
        <>
            <p>By: {name}</p>
        </>
    ) : <></>;
}