import { useParams } from "react-router-dom";
import { useDataSource } from "../../../hooks/useDataSource";
import { serverResource } from "../../../services/serverResource";
import ReadingLogSplitLayout from "../ReadingLogSplitLayout";
import ReadingLogEditForm from "./ReadingLogEditForm";

const API_URL = process.env.REACT_APP_API_URL;

export default function ReadingLogEdit() {
    const { logId } = useParams("id");
    const readingLog = useDataSource(serverResource(`${API_URL}/api/readingLogs/${logId}`));
    const { start, finish } = readingLog || {};

    return readingLog && (
        <ReadingLogSplitLayout component={<ReadingLogEditForm { ...{ readingLogId: logId, start, finish} } />} pageTitle="Edit Reading Log" />
    );
}