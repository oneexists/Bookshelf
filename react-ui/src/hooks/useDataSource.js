import { useEffect, useState } from "react"

export const useDataSource = (getResourceFunc) => {
    const [ resource, setResource ] = useState(null);

    useEffect(() => {
        getResourceFunc().then(setResource);
        // eslint-disable-next-line
    }, []);

    return resource;
}