import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import user from "@testing-library/user-event";
import BookDropdown from "./BookDropdown";

const LINKS = ["All", "Unread", "In Progress", "Finished"];

test("book dropdown button displays", () => {
    render(
        <MemoryRouter>
            <BookDropdown />
        </MemoryRouter>
    );

    const element = screen.getByRole("button", { name: "Books" });
    expect(element).toBeInTheDocument();
});

test("book dropdown opens on click", async () => {
    render(
        <MemoryRouter>
            <BookDropdown />
        </MemoryRouter>
    );

    const booksButton = screen.getByRole("button", { name: "Books" });
    user.click(booksButton);
    await screen.findAllByRole("link");

    for (let link of LINKS) {
        const element = screen.getByRole("link", { name: link });
        expect(element).toBeInTheDocument();
    }
});