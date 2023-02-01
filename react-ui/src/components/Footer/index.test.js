import { render, screen } from "@testing-library/react";
import Footer from "./index";

test("renders footer", () => {
    render(<Footer />);

    const footerElement = screen.getByText(/Skylar Lynner/);
    
    expect(footerElement).toBeInTheDocument();
});