import DangerButton from "../DangerButton";
import NavListItem from "../NavListItem";
import WarningNavLinkButton from "../WarningNavLinkButton";
import SecondaryNavLinkButton from "../SecondaryNavLinkButton";
import { ComponentList } from "../layouts/ComponentList";

export default function BookViewNavBar({handleDelete}) {
    const navItems = [
        <SecondaryNavLinkButton to="logs/add" text="Add Dates Read" />,
        <SecondaryNavLinkButton to="quotes/add" text="Add a Quote" />,
        <SecondaryNavLinkButton to="notes/add" text="Add a Note"/>,
        <WarningNavLinkButton url="edit" text="Edit Book Details"/>,
        <DangerButton text="Delete Book" onClick={handleDelete}/>
    ];

    return (
        <nav className="navbar navbar-expand-lg">
            <ul className="navbar-nav container-fluid">
                <ComponentList 
                    items={navItems}
                    resourceName="component"
                    itemComponent={NavListItem}
                />
            </ul>
        </nav>
    )
}