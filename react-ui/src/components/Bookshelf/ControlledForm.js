import { useEffect, useRef, useState } from "react";

export const ControlledForm = () => {
    const [ title, setTitle ] = useState("");
    const [ author, setAuthor ] = useState("");
    const [ pages, setPages ] = useState();
    const [ titleError, setTitleError ] = useState("");

    useEffect(() => {
        if (title.length < 2) {
            setTitleError("title must be two or more characters");
        } else {
            setTitleError("");
        }
    }, [title]);

    return (
        <form>
            {titleError && <p>{titleError}</p>}
            <input 
                name="title" 
                type="text" 
                placeholder="Title" 
                value={title} 
                onChange={(e) => setTitle(e.target.value)}
            />
            <input 
                name="author" 
                type="text" 
                placeholder="Author" 
                value={author}
                onChange={(e) => setAuthor(e.target.value)}
            />
            <input 
                name="pages" 
                type="number" 
                placeholder="Pages" 
                value={pages}
                onChange={(e) => setPages(Number(e.target.value))} 
            />
            <button type="submit">Submit</button>
        </form>
    );
}