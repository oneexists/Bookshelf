import SecondaryNavLinkButton from "../../../components/buttons/SecondaryNavLinkButton";
import PageLayout from "../../../components/layouts/PageLayout";
import { SplitScreen } from "../../../components/layouts/SplitScreen";

export default function BookshelfSplitLayout({ component, pageTitle }) {
    return (
        <PageLayout pageTitle={pageTitle}>
            <SplitScreen leftWeight={1} rightWeight={3}>
                <ul className="nav navbar-nav me-4">
                    <li className="nav-item mb-2"><SecondaryNavLinkButton url="books/add" text="Add Book" /></li>
                    <li className="nav-item mb-2"><SecondaryNavLinkButton url="books/in-progress" text="Currently Reading" /></li>
                    <li className="nav-item mb-2"><SecondaryNavLinkButton url="books/finished" text="View Finished Books" /></li>
                    <li className="nav-item mb-2"><SecondaryNavLinkButton url="books/unfinished" text="View Unfinished Books" /></li>
                </ul>

                {component}
            </SplitScreen>
        </PageLayout>
    )
}