import { withFinishedBooks } from "../../../hoc/withFinishedBooks";
import BookCards from "../BookCards";
import BookshelfSplitLayout from "../../../layouts/BookshelfSplitLayout";

export default function FinishedBooks() {
    const MESSAGE = "Go to any book, select 'Add Dates Read', then add a start and finish date to see it appear here!";
    const BookCardsWithLoader = withFinishedBooks(BookCards, MESSAGE);

    return (
        <BookshelfSplitLayout 
            component={<BookCardsWithLoader />} 
            pageTitle="Finished Books" 
        />
    );
}