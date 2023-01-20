import { useAuth } from "../../../../../hooks/useAuth";
import { withBooks } from "../../../hoc/withBooks";
import BookCards from "../BookCards";
import BookshelfSplitLayout from "../../../layouts/BookshelfSplitLayout";

export default function AllBooks() {
    const MESSAGE = "Add a book to get started!";
    const auth = useAuth();
    const BookCardsWithLoader = withBooks(BookCards, auth.user.id, MESSAGE);

    return (
        <BookshelfSplitLayout 
            component={<BookCardsWithLoader />} 
            pageTitle="Bookshelf" 
        />
    );
}