import { SplitScreen } from "../../../../components/layouts/SplitScreen";
import BookViewButtonBar from "./BookViewButtonBar";

export default function BookViewSplitLayout({ bookId, bookLogs }) {
    return (
        <SplitScreen leftWeight={1} rightWeight={3}>
            <BookViewButtonBar {...bookId} />

            <div className="mt-3">
                <h4>Reading Activity</h4>
                {bookLogs}

                <h4 className="mt-3">Quotes</h4>

                <h4 className="mt-3">Notes</h4>
            </div>
        </SplitScreen>
    )
}