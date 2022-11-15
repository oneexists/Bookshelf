export const LargeBookListItem = ({ book }) => {
    const { id, title, author, pages, language } = book;

    return(
        <>
            <h3>{id}: {title}</h3>
            <p>By: {author}</p>
            <p>Pages: {pages}</p>
            <p>Language: {language}</p>
        </>
    );
}