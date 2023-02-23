import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import PrimaryNavLinkButton from "./PrimaryNavLinkButton";

function renderComponent() {
    render(
        <MemoryRouter>
            <PrimaryNavLinkButton url="google.com" text="Link Text" marginEnd={3} />
        </MemoryRouter>
    );
}

test("primary nav link button displays text and adds margin", () => {
    renderComponent();

    const secondaryNavLink = screen.getByText("Link Text");

    expect(secondaryNavLink).toBeInTheDocument();
    expect(secondaryNavLink).toHaveClass("btn btn-primary w-100 me-3");
});