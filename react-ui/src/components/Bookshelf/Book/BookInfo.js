export const BookInfo = ({ book }) => {
    const { title, pages, language } = book || {};

    return book ? (
        <>
            <h3>{title}</h3>
            <p>Pages: {pages}</p>
            <p>Language: {language}</p>
        </>
    ) : <p>Loading...</p>;
}