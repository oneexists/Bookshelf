export default function ReadingLogDetails({ log }) {
    const { start, finish } = log;

    return finish ? (
        <>
            <div className="flex-fill">{new Date(start.replace(/-/g, '/')).toLocaleDateString()} - {new Date(finish.replace(/-/g, '/')).toLocaleDateString()}</div>
        </>
    ) : (
        <>
            <div className="flex-fill">{new Date(start.replace(/-/g, '/')).toLocaleDateString()}</div>
        </>
    );
}