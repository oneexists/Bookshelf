import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import NotFound from ".";

test("not found displays image and paragraph", () => {
    render(<NotFound />);
    
    const image = screen.getByRole("img");
    const paragraph = screen.getByText(/The page you were looking for/);
    
    expect(image).toBeInTheDocument();
    expect(paragraph).toBeInTheDocument();
});