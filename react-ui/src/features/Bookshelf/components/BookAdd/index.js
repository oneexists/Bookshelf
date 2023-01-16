import PageLayout from "../../../../components/layouts/PageLayout";
import BookAddForm from "./BookAddForm";

export default function BookAdd() {
    return (
        <PageLayout pageTitle="Add Book">
            <BookAddForm />
        </PageLayout>
    );
}