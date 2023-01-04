import Title from "../Title";
import Background from "./Background";

export default function PageLayout({ pageTitle, children }) {
    return (
        <Background>
            <Title text={pageTitle} />
            {children}
        </Background>
    )
}