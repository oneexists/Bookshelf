import { withEditableResource } from "./withEditableResource";

export const BookInfoForm = 
            withEditableResource(({ book, onChangeBook, onSaveBook, onResetBook }) => {
    const { title, pages, language } = book || {};

    return book ? (
        <>
            <label>
                Title:
                <input type="text" value={title} onChange={(e) => onChangeBook({ title: e.target.value })} />
            </label>
            <label>
                Pages:
                <input type="number" value={pages} onChange={(e) => onChangeBook({ pages: Number(e.target.value) })} />
            </label>
            <label>
                Language:
                <input type="text" value={language} onChange={(e) => onChangeBook({ language: e.target.value })} />
            </label>
            <button type="button" onClick={onResetBook}>Reset</button>
            <button type="submit" onClick={onSaveBook}>Save Changes</button>
        </>
    ) : <p>Loading...</p>;
}, 'http://localhost:8080/api/books/2', 'book');