export default function BookDetail({ authorName, language, pages }) {
    return (
        <>
            <div className="flex-fill">By: {authorName}</div>
            <div className="flex-fill">Language: {language}</div>
            <div className="flex-fill">Pages: {pages}</div>
        </>
    );
}