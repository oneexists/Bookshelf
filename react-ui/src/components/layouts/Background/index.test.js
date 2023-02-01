import { render, screen } from "@testing-library/react";
import Background from "./index";

test("background renders main", () => {
    render(<Background />);

    const main = screen.getByRole("main");
    
    expect(main).toBeInTheDocument();
});