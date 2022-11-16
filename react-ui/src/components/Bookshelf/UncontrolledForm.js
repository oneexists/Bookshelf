import { useRef } from "react";

export const UncontrolledForm = () => {
    const titleRef = useRef();
    const authorRef = useRef();
    const pagesRef = useRef();

    const handleSubmit = (evt) => {
        evt.preventDefault();

        console.log(titleRef.current.value);
        console.log(authorRef.current.value);
        console.log(pagesRef.current.value);
    };

    return (
        <form onSubmit={handleSubmit}>
            <input name="title" type="text" placeholder="Title" ref={titleRef} />
            <input name="author" type="text" placeholder="Author" ref={authorRef} />
            <input name="pages" type="number" placeholder="Pages" ref={pagesRef} />
            <input type="submit" value="Submit" />
        </form>
    );
}