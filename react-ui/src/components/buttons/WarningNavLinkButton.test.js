import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import WarningNavLinkButton from "./WarningNavLinkButton";

function renderComponent() {
    render(
        <MemoryRouter>
            <WarningNavLinkButton url="google.com" text="Warning Text" marginEnd={2} />
        </MemoryRouter>
    );
}

test("warning nav link button displays text and adds margin", () => {
    renderComponent();

    const warningNavLink = screen.getByText("Warning Text");

    expect(warningNavLink).toBeInTheDocument();
    expect(warningNavLink).toHaveClass("btn btn-warning w-100 me-2");
});