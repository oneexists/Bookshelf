import { useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useInput } from "../../../../hooks/useInput";
import { editLog } from "../../../../services/readingLogService";
import ErrorPanel from "../../../../components/forms/ErrorPanel";
import SectionLabel from "../../../../components/forms/SectionLabel";
import SubmitPanel from "../../../../components/forms/SubmitPanel";

export default function ReadingLogEditForm({ start, finish }) {
    const { logId } = useParams();
    const errorRef = useRef();
    const navigate = useNavigate();

    const [ startProps ] = useInput(start);
    const [ finishProps ] = useInput(finish);
    const [ errorMsg, setErrorMsg ] = useState([]);

    const handleSubmit = async(evt) => {
        evt.preventDefault();

        editLog({ 
            readingLogId: logId, 
            start: startProps.value,
            finish: finishProps.value
        }).then(() => {
            navigate("/");
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