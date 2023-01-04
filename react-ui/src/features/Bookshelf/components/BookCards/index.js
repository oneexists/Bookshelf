import { ComponentList } from "../../../../components/layouts/ComponentList";
import BookCard from "./BookCard";

export default function BookCards({ books }) {
    return (
        <div className="row row-cols-3 g2">
            <ComponentList 
                items={books}
                resourceName="book"
                itemComponent={BookCard}
            />
        </div>
    )
}