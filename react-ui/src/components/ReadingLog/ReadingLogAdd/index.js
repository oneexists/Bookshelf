import ReadingLogSplitLayout from "../ReadingLogSplitLayout";
import ReadingLogAddForm from "./ReadingLogAddForm";

export default function ReadingLogAdd() {    
    return (
        <ReadingLogSplitLayout component={<ReadingLogAddForm />} pageTitle="Add Reading Log" />
    );
}