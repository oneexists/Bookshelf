import ReadingLogDetails from "./ReadingLogDetails";
import { useDataSource } from "../../../../hooks/useDataSource";
import { serverResource } from "../../../../services/serverResource";
import { ComponentList } from "../../../../components/layouts/ComponentList";

export default function ReadingLogs({ url }) {
    const result = useDataSource(serverResource(url));
    const { _embedded } = result || {};
    const { readingLogs } = _embedded || {};

    return result && (
        <>
            <ComponentList 
                items={readingLogs}
                resourceName="log"
                itemComponent={ReadingLogDetails}
            />
        </>
    );
}