export const BookInfo = ({ book }) => {
    const { title, author, pages, language } = book || {};

    return book ? (
        <>
            <h3>{title}</h3>
            <p>By: {author}</p>
            <p>Pages: {pages}</p>
            <p>Language: {language}</p>
        </>
    ) : <p>Loading...</p>;
}