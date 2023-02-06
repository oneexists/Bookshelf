import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import NotFound from "./index";

test("not found displays title, image and paragraph", () => {
    render(<NotFound />);
    
    const heading = screen.getByRole("heading");
    const image = screen.getByRole("img");
    const paragraph = screen.getByText(/The page you were looking for/);
    
    expect(heading).toBeInTheDocument();
    expect(heading).toHaveTextContent("That was strange...");
    expect(image).toBeInTheDocument();
    expect(paragraph).toBeInTheDocument();
});