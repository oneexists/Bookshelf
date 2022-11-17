import { ComponentList } from "../layouts/ComponentList";
import BookTableRow from "./BookTableRow";

export default function BookTable({ books }) {
    return (
        <table className="table">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Pages</th>
                    <th>Language</th>
                </tr>
            </thead>
            <tbody>
                <ComponentList
                    items={books}
                    resourceName="book"
                    itemComponent={BookTableRow}
                />
            </tbody>
        </table>
    );
}