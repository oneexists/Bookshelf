import { useEffect, useState } from "react"

export const useResource = (resourceUrl) => {
    const [ resource, setResource ] = useState(null);

    useEffect(() => {
        fetch(resourceUrl).then(r => r.json()).then(setResource);
    }, [resourceUrl]);

    return resource;
}