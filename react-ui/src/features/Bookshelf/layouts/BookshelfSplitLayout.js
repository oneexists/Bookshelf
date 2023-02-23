import PrimaryNavLinkButton from "../../../components/buttons/PrimaryNavLinkButton";
import PageLayout from "../../../components/layouts/PageLayout";
import { SplitScreen } from "../../../components/layouts/SplitScreen";

export default function BookshelfSplitLayout({ component, pageTitle }) {
  return (
    <PageLayout pageTitle={pageTitle}>
      <SplitScreen leftWeight={1} rightWeight={3}>
        <ul className="nav navbar-nav me-4">
          <li className="nav-item mb-2">
            <PrimaryNavLinkButton url="/books/add" text="Add Book" />
          </li>
        </ul>

        {component}
      </SplitScreen>
    </PageLayout>
  );
}
