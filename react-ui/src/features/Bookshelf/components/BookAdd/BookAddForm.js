import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../../../hooks/useAuth";
import { useInput } from "../../../../hooks/useInput";
import { createBook } from "../../../../services/bookService";
import ErrorPanel from "../../../../components/forms/ErrorPanel";
import SectionLabel from "../../../../components/forms/SectionLabel";
import SubmitPanel from "../../../../components/forms/SubmitPanel";

export default function BookAddForm() {
    const auth = useAuth();
    const navigate = useNavigate();

    const errorRef = useRef();
    const titleRef = useRef();
    
    const [ titleProps ] = useInput("");
    const [ authorProps ] = useInput("");
    const [ pagesProps ] = useInput("");
    const [ languageProps ] = useInput("");
    const [ errorMsg, setErrorMsg ] = useState([]);

    useEffect(() => {
        titleRef.current.focus();
    }, []);
    
    const handleSubmit = async (evt) => {
        evt.preventDefault();

        createBook({
            title: titleProps.value, 
            pages: pagesProps.value, 
            language: languageProps.value,
            author: authorProps.value,
            appUserId: auth.user.id
        }).then(() => {
            navigate("/");
        }).catch((r) => {
            setErrorMsg((r) => setErrorMsg(["Error saving book"]));
        });
    }
    
    return (
        <>
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
        </>
    )
}