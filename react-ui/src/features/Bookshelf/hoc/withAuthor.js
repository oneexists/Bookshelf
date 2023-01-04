import { useDataSource } from "../../../hooks/useDataSource";
import { serverResource } from "../../../services/serverResource";

export const withAuthor = (Component, url) => {
    return props => {
        const author = useDataSource(serverResource(url));
        const { name } = author || {};

        return name && (
            <Component { ...props } name={name} />
        );
    };
}