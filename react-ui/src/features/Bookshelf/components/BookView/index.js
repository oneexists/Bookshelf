import { useParams } from "react-router-dom";
import { useDataSource } from "../../../../hooks/useDataSource";
import { serverResource } from "../../../../services/serverResource";
import BookDetails from "../../layouts/BookDetails";
import BookLogs from "../../../ReadingLog/components/BookLogs";
import { BOOKS_URL } from "../../../../config/bookshelfApi";
import PageLayout from "../../../../components/layouts/PageLayout";
import BookViewButtonBar from "./BookViewButtonBar";
import BookViewSplitLayout from "./BookViewSplitLayout";

export default function BookView() {
    const { id } = useParams("id");
    const book = useDataSource(serverResource(`${BOOKS_URL}/${id}?projection=inlineAuthor`));
    const { title, author, pages, language } = book || {};

    return book ? (
        <PageLayout pageTitle={title}>
            <BookViewButtonBar bookId={id} />

            <BookViewSplitLayout 
                details={<BookDetails { ...{author, language, pages} } />} 
                bookLogs={<BookLogs url={book._links.readingLogs.href} />} 
            />
        </PageLayout>
    ) : <p>Loading...</p>;
}