import ReadingLogSplitLayout from "../../layouts/ReadingLogSplitLayout";
import ReadingLogAddForm from "./ReadingLogAddForm";

export default function ReadingLogAdd() {    
    return (
        <ReadingLogSplitLayout 
            component={<ReadingLogAddForm />} 
            pageTitle="Add Reading Log" 
        />
    );
}