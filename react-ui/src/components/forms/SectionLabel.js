export default function SectionLabel({id, text, children}) {
    return (
        <section className="form-group mb-3">
            <label htmlFor={id} className="form-label">{text}</label>
            {children}
        </section>
    )
}