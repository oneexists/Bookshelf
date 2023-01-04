import { useParams } from "react-router-dom";
import { useDataSource } from "../../../../hooks/useDataSource";
import { serverResource } from "../../../../services/serverResource";
import BookEditForm from "./BookEditForm";
import { withAuthor } from "../../hoc/withAuthor";
import { BOOKS_URL } from "../../../../config/bookshelfApi";
import PageLayout from "../../../../components/layouts/PageLayout";

export default function BookEdit() {
    const { id } = useParams("id");
    const book = useDataSource(serverResource(`${BOOKS_URL}/${id}`));
    const { title, pages, language } = book || {};
    const href = book ? book._links.author.href : {};

    const BookEditFormLoadedAuthor = withAuthor(BookEditForm, href);

    return book && (
        <PageLayout pageTitle="Edit Book">
            <BookEditFormLoadedAuthor { ...{ title, pages, language } } />
        </PageLayout>
    );
}