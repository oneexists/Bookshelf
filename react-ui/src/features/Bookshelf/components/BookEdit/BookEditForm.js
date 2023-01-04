import { useEffect, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useAuth } from "../../../../hooks/useAuth";
import { useInput } from "../../../../hooks/useInput";
import { updateBook } from "../../../../services/bookService";
import ErrorPanel from "../../../../components/forms/ErrorPanel";
import SectionLabel from "../../../../components/forms/SectionLabel";
import SubmitPanel from "../../../../components/forms/SubmitPanel";

export default function BookEditForm({ title, name, language, pages }) {
    const auth = useAuth();
    const { id } = useParams();
    const navigate = useNavigate();

    const errorRef = useRef();
    const titleRef = useRef();

    const [ titleProps, resetTitle ] = useInput(title);
    const [ authorProps, resetAuthor ] = useInput(name);
    const [ pagesProps, resetPages ] = useInput(pages);
    const [ languageProps, resetLanguage ] = useInput(language);
    const [ errorMsg, setErrorMsg ] = useState([]);

    useEffect(() => {
        titleRef.current.focus();
    }, []);

    const handleSubmit = async (evt) => {
        evt.preventDefault();

        updateBook({ 
            appUserId: auth.user.id,
            bookId: id, 
            title: titleProps.value, 
            author: authorProps.value,
            pages: pagesProps.value, 
            language: languageProps.value 
        }).then(() => {
            navigate("/");
            resetTitle();
            resetAuthor();
            resetPages();
            resetLanguage();
        }).catch((r) => setErrorMsg(["Error updating book details"]));
        
    }

    return (
        <>
            <ErrorPanel errorRef={errorRef} errorMsg={errorMsg} />

            <form onSubmit={handleSubmit}>
                <SectionLabel id="title" text="Title:">
                    <input
                        type="text"
                        aria-label="edit book title"
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

                <SubmitPanel text="Save" />
            </form>
        </>
    );
}