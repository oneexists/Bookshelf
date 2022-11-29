export default function BookDetail({ author, language, pages }) {
    return (
        <>
            <div className="flex-fill">By: {author.name}</div>
            <div className="flex-fill">Language: {language}</div>
            <div className="flex-fill">Pages: {pages}</div>
        </>
    );
}