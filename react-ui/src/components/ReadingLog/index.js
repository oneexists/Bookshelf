export default function ReadingLog({ log }) {
    const { start, finish } = log;

    return finish ? (
        <>
            <div className="flex-fill">{new Date(start).toLocaleDateString()} - {new Date(finish).toLocaleDateString()}</div>
        </>
    ) : (
        <>
            <div className="flex-fill">{new Date(start).toLocaleDateString()}</div>
        </>
    );
}