import { useEffect, useRef, useState } from "react";
import Background from "../Background";
import ErrorPanel from "../forms/ErrorPanel";
import Title from "../Title";
import SectionLabel from "../forms/SectionLabel";
import SubmitPanel from "../forms/SubmitPanel";
import { useInput } from "../../hooks/useInput";

export default function BookAdd() {
    const errorRef = useRef();
    const titleRef = useRef();

    const [ titleProps, resetTitle ] = useInput("");
    const [ authorProps, resetAuthor ] = useInput("");
    const [ pagesProps, resetPages ] = useInput("");
    const [ languageProps, resetLanguage ] = useInput("");
    const [ errorMsg, setErrorMsg ] = useState([]);

    useEffect(() => {
        titleRef.current.focus();
    }, []);

    const handleSubmit = async (evt) => {
        evt.preventDefault();

    }

    return (
        <Background>
            <Title text="Add Book" />

            <ErrorPanel errorRef={errorRef} errorMsg={errorMsg} />

            <form onSubmit={handleSubmit}>
                <SectionLabel id="title" text="Title:">
                    <input 
                        type="text"
                        aria-label="book title"
                        ref={titleRef}
                        className="form-control"
                        id="title"
                        required
                        { ...titleProps }
                    />
                </SectionLabel>

                <SectionLabel id="author" text="Author:">
                    <input
                        type="text"
                        aria-label="book author"
                        className="form-control"
                        id="author"
                        required
                        { ...authorProps }
                    />
                </SectionLabel>

                <SectionLabel id="pages" text="Pages:">
                    <input 
                        type="number"
                        aria-label="book pages"
                        className="form-control"
                        id="pages"
                        required
                        { ...pagesProps }
                    />
                </SectionLabel>

                <SectionLabel id="langauge" text="Language:">
                    <input 
                        type="text"
                        aria-label="book language"
                        className="form-control"
                        id="language"
                        { ...languageProps }
                    />
                </SectionLabel>

                <SubmitPanel text="Add" />
            </form>
        </Background>
    );
}