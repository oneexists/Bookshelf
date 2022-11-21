import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import NotFound from ".";

test("renders not found", () => {
    render(<NotFound />);
    
    const h2 = screen.getByText(/That was Strange.../);
    expect(h2).toBeInTheDocument();

    const p = screen.getByText(/The page you were looking for/);
    expect(p).toBeInTheDocument();
});