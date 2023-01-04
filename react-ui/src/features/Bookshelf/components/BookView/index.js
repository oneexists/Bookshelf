import { useParams } from "react-router-dom";
import { useDataSource } from "../../../../hooks/useDataSource";
import { serverResource } from "../../../../services/serverResource";
import { SplitScreen } from "../../../../components/layouts/SplitScreen";
import BookDetails from "../BookDetails";
import BookLogs from "../../../ReadingLog/components/BookLogs";
import { BOOKS_URL } from "../../../../config/bookshelfApi";
import PageLayout from "../../../../components/layouts/PageLayout";
import BookViewButtonBar from "./BookViewButtonBar";

export default function BookView() {
    const { id } = useParams("id");
    const book = useDataSource(serverResource(`${BOOKS_URL}/${id}?projection=inlineAuthor`));
    const { title, author, pages, language } = book || {};

    return book ? (
        <PageLayout pageTitle={title}>
            <BookViewButtonBar bookId={id} />

            <SplitScreen leftWeight={1} rightWeight={3}>
                <div id="book-details" className="m-4 p-2">
                    <BookDetails { ...{author, language, pages} } />
                </div>

                <div className="mt-3">
                    <h4>Reading Activity</h4>
                    <BookLogs url={book._links.readingLogs.href} />

                    <h4 className="mt-3">Quotes</h4>

                    <h4 className="mt-3">Notes</h4>
                </div>
            </SplitScreen>
        </PageLayout>
    ) : <p>Loading...</p>;
}