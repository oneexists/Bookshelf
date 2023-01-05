import { memo, useCallback } from "react";
import DangerButton from "../../../components/buttons/DangerButton";
import { deleteLogById } from "../../../services/readingLogService";
import SecondaryNavLinkButton from "../../../components/buttons/SecondaryNavLinkButton";
import ButtonBar from "../../../components/layouts/ButtonBar";

const DeleteButton = memo(({ onClick }) => (
    <DangerButton text="Delete" handleClick={onClick} />
));

export default function ReadingLogDetails({ log }) {
    const { readingLogId: logId, start, finish } = log;

    const handleDelete = useCallback(() => (
        deleteLogById(logId).then(window.location.reload(false))
    ), [logId]);

    return finish ? (
        <>
            <div className="bg-white row m-1 p-1">
                <div className="col-8">
                    {new Date(start.replace(/-/g, '/')).toLocaleDateString()} - {new Date(finish.replace(/-/g, '/')).toLocaleDateString()}
                </div>
                <div className="col-auto">
                    <ButtonBar>
                        <SecondaryNavLinkButton url={`logs/edit/${logId}`} text="Edit" marginEnd={2} />
                        <DeleteButton onClick={handleDelete} />
                    </ButtonBar>
                </div>
            </div>
        </>
    ) : (
        <>
            <div className="flex-fill">{new Date(start.replace(/-/g, '/')).toLocaleDateString()}</div>
        </>
    );
}