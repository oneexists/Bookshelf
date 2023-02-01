import { render, screen } from "@testing-library/react";
import Title from "./index";

test("title displays a heading", () => {
    render(<Title text="Title Text" />);

    const heading = screen.getByRole("heading");

    expect(heading).toBeInTheDocument();
});