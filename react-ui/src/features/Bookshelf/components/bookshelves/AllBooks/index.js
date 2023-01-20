import { useAuth } from "../../../../../hooks/useAuth";
import { withBooks } from "../../../hoc/withBooks";
import BookCards from "../BookCards";
import BookshelfSplitLayout from "../../../layouts/BookshelfSplitLayout";

export default function AllBooks() {
    const auth = useAuth();
    const BookCardsWithLoader = withBooks(BookCards, auth.user.id);

    return (
        <BookshelfSplitLayout 
            component={<BookCardsWithLoader />} 
            pageTitle="Bookshelf" 
        />
    );
}