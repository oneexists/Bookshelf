import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import Footer from ".";

test("renders footer", () => {
    render(<Footer />);
    const footerElement = screen.getByText(/&copy;2022 | Skylar Lynner/);
    expect(footerElement).toBeInTheDocument();
});