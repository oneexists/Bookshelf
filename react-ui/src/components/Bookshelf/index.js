import { findAll } from "../../services/bookService";
import { LargeBookListItem } from "./Book/LargeBookListItem";
import { SmallBookListItem } from "./Book/SmallBookListItem";
import { NumberedList } from "./NumberedList";
import { RegularList } from "./RegularList";

export default function Bookshelf() {
    return (
        <main className="container mt-3">
            <h1>Small Book List</h1>
            <NumberedList 
                items={findAll()} 
                resourceName="book" 
                itemComponent={SmallBookListItem}
            />
            <h1>Large Book List</h1>
            <RegularList 
                items={findAll()} 
                resourceName="book" 
                itemComponent={LargeBookListItem}
            />
        </main>
    );
}