import { withEditableBook } from "./withEditableBook";

export const BookInfoForm = 
            withEditableBook(({ book, onChangeBook, onSaveBook, onResetBook }) => {
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
}, 2);