import { SplitScreen } from "../../../../components/layouts/SplitScreen";

export default function BookViewSplitLayout({ details, bookLogs }) {
    return (
        <SplitScreen leftWeight={1} rightWeight={3}>
                <div id="book-details" className="m-4 p-2">
                    {details}
                </div>

                <div className="mt-3">
                    <h4>Reading Activity</h4>
                    {bookLogs}

                    <h4 className="mt-3">Quotes</h4>

                    <h4 className="mt-3">Notes</h4>
                </div>
        </SplitScreen>
    )
}