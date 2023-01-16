import { useParams } from "react-router-dom";
import { useDataSource } from "../../../hooks/useDataSource";
import { serverResource } from "../../../services/serverResource";
import { BOOKS_URL } from "../../../config/bookshelfApi";
import { SplitScreen } from "../../../components/layouts/SplitScreen";
import PageLayout from "../../../components/layouts/PageLayout";
import BookDetails from "../../Bookshelf/layouts/BookDetails";

export default function ReadingLogSplitLayout({ component, pageTitle }) {
    const { id } = useParams("id");
    const book = useDataSource(serverResource(`${BOOKS_URL}/${id}?projection=inlineAuthor`));
    const { title, author, pages, language } = book || {};

    return book ? (
        <PageLayout pageTitle={pageTitle}>
            <SplitScreen leftWeight={1} rightWeight={3}>
            <div id="book-details" className="m-4 p-2">
                <div className="flex-fill">{title}</div>
                    <BookDetails { ...{author, language, pages} } />
                </div>

                {component}
            </SplitScreen>
        </PageLayout>
    ) : <p>Loading...</p>;
}