import { withInProgressBooks } from "../../../hoc/withInProgressBooks";
import BookCards from "../BookCards";
import BookshelfSplitLayout from "../../../layouts/BookshelfSplitLayout";

export default function InProgressBooks() {
    const MESSAGE = "Go to any book, select 'Add Dates Read' and add a start date to see it appear here!";
    const BookCardsWithLoader = withInProgressBooks(BookCards, MESSAGE);

    return (
        <BookshelfSplitLayout 
            component={<BookCardsWithLoader />} 
            pageTitle="Currently Reading" 
        />
    );
}