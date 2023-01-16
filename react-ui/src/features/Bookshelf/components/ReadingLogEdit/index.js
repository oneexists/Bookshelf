import { useParams } from "react-router-dom";
import { useDataSource } from "../../../../hooks/useDataSource";
import { serverResource } from "../../../../services/serverResource";
import ReadingLogSplitLayout from "../../layouts/ReadingLogSplitLayout";
import ReadingLogEditForm from "./ReadingLogEditForm";
import { READING_LOG_URL } from "../../../../config/bookshelfApi";

export default function ReadingLogEdit() {
    const { logId } = useParams("id");
    const readingLog = useDataSource(serverResource(`${READING_LOG_URL}/${logId}`));
    const { start, finish } = readingLog || {};

    return readingLog && (
        <ReadingLogSplitLayout 
            component={<ReadingLogEditForm { ...{ start, finish} } />} 
            pageTitle="Edit Reading Log" 
        />
    );
}