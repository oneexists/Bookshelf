import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import SecondaryNavLinkButton from "./SecondaryNavLinkButton";

function renderComponent() {
    render(
        <MemoryRouter>
            <SecondaryNavLinkButton url="google.com" text="Link Text" marginEnd={3} />
        </MemoryRouter>
    );
}

test("secondary nav link button displays text and adds margin", () => {
    renderComponent();

    const secondaryNavLink = screen.getByText("Link Text");

    expect(secondaryNavLink).toBeInTheDocument();
    expect(secondaryNavLink).toHaveClass("btn btn-secondary w-100 me-3");
});