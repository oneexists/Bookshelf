import { useAuth } from "../../../../../hooks/useAuth";
import { withBooks } from "../../../hoc/withBooks";
import BookCards from "../BookCards";
import BookshelfSplitLayout from "../../../layouts/BookshelfSplitLayout";

export default function UnreadBooks() {
    const MESSAGE = "Add a book you haven't read yet to see it here!";
    const auth = useAuth();
    const BookCardsWithLoader = withBooks(BookCards, auth.user.id, MESSAGE);

    return (
        <BookshelfSplitLayout 
            component={<BookCardsWithLoader />} 
            pageTitle="Unread Books" 
        />
    );
}