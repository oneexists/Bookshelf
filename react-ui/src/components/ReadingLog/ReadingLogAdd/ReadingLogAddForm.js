import { useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useInput } from "../../../hooks/useInput";
import { createLog } from "../../../services/readingLogService";
import ErrorPanel from "../../forms/ErrorPanel";
import SectionLabel from "../../forms/SectionLabel";
import SubmitPanel from "../../forms/SubmitPanel";

export default function ReadingLogAddForm() {
    const navigate = useNavigate();
    const { id } = useParams("id");

    const errorRef = useRef();

    const [ startProps, resetStart ] = useInput();
    const [ finishProps, resetFinish ] = useInput();
    const [ errorMsg, setErrorMsg ] = useState([]);

    const handleSubmit = async(evt) => {
        evt.preventDefault();

        createLog({ 
            start: startProps.value, 
            finish: finishProps.value, 
            bookId: id 
        }).then(() => {
            navigate("/");
            resetStart();
            resetFinish();
        }).catch((r) => setErrorMsg(r));
    }

    return (
        <>
            <ErrorPanel errorRef={errorRef} errorMsg={errorMsg} />

            <form onSubmit={handleSubmit}>
                <SectionLabel id="start" text="Start:">
                    <input 
                        type="date"
                        aria-label="start date"
                        className="form-control"
                        required
                        { ...startProps }
                    />
                </SectionLabel>

                <SectionLabel id="finish" text="Finish:">
                    <input 
                        type="date"
                        aria-label="finish date"
                        className="form-control"
                        { ...finishProps }
                    />
                </SectionLabel>

                <SubmitPanel text="Save" />
            </form>
        </>
    );
}