export const SmallBookListItem = ({ book }) => {
    const { id, title } = book;

    return(
        <p>ID: {id} Title: {title}</p>
    );
}