import PageLayout from "../../components/layouts/PageLayout";
import Introduction from "./Introduction";
import Resources from "./Resources";

export default function Home() {
    return (
        <PageLayout pageTitle="Bullet Journal Bookshelf">
            <Introduction />
            <Resources />
        </PageLayout>
    );
}