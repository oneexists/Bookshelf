import { render, screen } from "@testing-library/react";
import Background from "./index";

test("background renders main with classes", () => {
    render(<Background />);

    const main = screen.getByRole("main");
    
    expect(main).toBeInTheDocument();
    expect(main).toHaveClass("container p-3");
});