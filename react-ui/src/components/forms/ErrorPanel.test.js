import { render, screen } from "@testing-library/react";
import ErrorPanel from "./ErrorPanel";

test("error panel with an error has alert classes and displays error messages", () => {
    render(<ErrorPanel errorMsg={["Failed to fetch"]} />);

    const errorPanel = screen.getByRole("alert");
    const errorList = screen.getAllByRole("listitem");

    expect(errorPanel).toBeInTheDocument();
    expect(errorPanel).toHaveClass("alert alert-danger");
    expect(errorList).toHaveLength(1);
});

test("error panel is offscreen if no error messages exist", () => {
    render(<ErrorPanel errorMsg={[]} />);

    const errorPanel = screen.getByRole("alert");

    expect(errorPanel).toBeInTheDocument();
    expect(errorPanel).toHaveClass("offscreen");
});