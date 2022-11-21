import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import Home from ".";

test("renders home", () => {
    render(<Home />);

    const h2 = screen.getByText(/Bullet Journal Bookshelf/);
    expect(h2).toBeInTheDocument();

    const firstParagraph = screen.getByText(/Keep all/);
    expect(firstParagraph).toBeInTheDocument();

    const secondParagraph = screen.getByText(/Inspired by/);
    expect(secondParagraph).toBeInTheDocument();
});