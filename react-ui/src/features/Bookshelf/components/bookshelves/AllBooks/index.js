import { withBooks } from "../../../hoc/withBooks";
import BookCards from "../BookCards";
import BookshelfSplitLayout from "../../../layouts/BookshelfSplitLayout";

export default function AllBooks() {
    const MESSAGE = "Add a book to get started!";
    const BookCardsWithLoader = withBooks(BookCards, MESSAGE);

    return (
        <BookshelfSplitLayout 
            component={<BookCardsWithLoader />} 
            pageTitle="Bookshelf" 
        />
    );
}