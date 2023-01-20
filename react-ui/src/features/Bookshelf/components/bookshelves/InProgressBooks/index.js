import { useAuth } from "../../../../../hooks/useAuth";
import { withBooks } from "../../../hoc/withBooks";
import BookCards from "../BookCards";
import BookshelfSplitLayout from "../../../layouts/BookshelfSplitLayout";

export default function InProgressBooks() {
    const MESSAGE = "Go to any book, select 'Add Dates Read' and add a start date to see it appear here!";
    const auth = useAuth();
    const BookCardsWithLoader = withBooks(BookCards, auth.user.id, MESSAGE);

    return (
        <BookshelfSplitLayout 
            component={<BookCardsWithLoader />} 
            pageTitle="Currently Reading" 
        />
    );
}