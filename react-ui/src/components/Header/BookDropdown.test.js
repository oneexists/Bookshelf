import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import BookDropdown from "./BookDropdown";

const LINKS = ["Books", "All", "Unread", "In Progress", "Finished"];

test("book dropdown displays links", () => {
    render(
        <MemoryRouter>
            <BookDropdown />
        </MemoryRouter>
    );

    for (let i in LINKS) {
        const element = screen.getByRole("link", { name: LINKS[i] });
        expect(element).toBeInTheDocument();
    }
});