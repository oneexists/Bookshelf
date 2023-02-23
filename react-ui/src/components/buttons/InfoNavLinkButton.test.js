import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import InfoNavLinkButton from "./InfoNavLinkButton";

function renderComponent() {
    render(
        <MemoryRouter>
            <InfoNavLinkButton url="google.com" text="Link Text" marginEnd={3} />
        </MemoryRouter>
    );
}

test("info nav link button displays text and adds margin", () => {
    renderComponent();

    const secondaryNavLink = screen.getByText("Link Text");

    expect(secondaryNavLink).toBeInTheDocument();
    expect(secondaryNavLink).toHaveClass("btn btn-info w-100 me-3");
});