export default function BookDetail({ authorName, language, pages }) {
    return (
        <section>
            <p>By: {authorName}</p>
            <p>Language: {language}</p>
            <p>Pages: {pages}</p>
        </section>
    );
}