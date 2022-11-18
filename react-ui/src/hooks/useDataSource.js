import { useEffect, useState } from "react"

export const useDataSource = (getResourceFunc) => {
    const [ resource, setResource ] = useState(null);

    useEffect(() => {
        getResourceFunc().then(setResource);
    }, [getResourceFunc]);

    return resource;
}