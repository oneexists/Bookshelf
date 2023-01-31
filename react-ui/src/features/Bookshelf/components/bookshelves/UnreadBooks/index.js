import { withUnreadBooks } from "../../../hoc/withUnreadBooks";
import BookCards from "../BookCards";
import BookshelfSplitLayout from "../../../layouts/BookshelfSplitLayout";

export default function UnreadBooks() {
    const MESSAGE = "Add a book you haven't read yet to see it here!";
    const BookCardsWithLoader = withUnreadBooks(BookCards, MESSAGE);

    return (
        <BookshelfSplitLayout 
            component={<BookCardsWithLoader />} 
            pageTitle="Unread Books" 
        />
    );
}