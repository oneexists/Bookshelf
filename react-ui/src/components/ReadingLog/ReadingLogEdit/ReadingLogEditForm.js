import { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useInput } from "../../../hooks/useInput";
import { editLog } from "../../../services/readingLogService";
import ErrorPanel from "../../forms/ErrorPanel";
import SectionLabel from "../../forms/SectionLabel";
import SubmitPanel from "../../forms/SubmitPanel";

export default function ReadingLogEditForm({ readingLogId, start, finish }) {
    const errorRef = useRef();
    const navigate = useNavigate();

    const [ startProps, resetStart ] = useInput(start);
    const [ finishProps, resetFinish ] = useInput(finish);
    const [ errorMsg, setErrorMsg ] = useState([]);

    const handleSubmit = async(evt) => {
        evt.preventDefault();

        editLog({ 
            readingLogId, 
            start: startProps.value,
            finish: finishProps.value
        }).then(() => {
            navigate("/");
            resetStart();
            resetFinish();
        }).catch((r) => setErrorMsg(r));
    };

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
                
                <SubmitPanel text="Update" />
            </form>
        </>
    );
}