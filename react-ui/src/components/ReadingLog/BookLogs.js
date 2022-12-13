import ReadingLog from ".";
import { useDataSource } from "../../hooks/useDataSource";
import { serverResource } from "../../services/serverResource";
import { ComponentList } from "../layouts/ComponentList";

export default function BookLogs({ url }) {
    const result = useDataSource(serverResource(url));
    const { _embedded } = result || {};
    const { readingLogs } = _embedded || {};

    return result && (
        <>
            <ComponentList 
                items={readingLogs}
                resourceName="log"
                itemComponent={ReadingLog}
            />
        </>
    );
}