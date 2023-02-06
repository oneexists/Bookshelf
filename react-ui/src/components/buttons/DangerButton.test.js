import { render, screen } from "@testing-library/react";
import DangerButton from "./DangerButton";

test("danger button displays text and adds margin", () => {
    render(<DangerButton text="Button Text" marginEnd={2} handleClick={() => {}} />);

    const button = screen.getByText("Button Text");

    expect(button).toBeInTheDocument();
    expect(button).toHaveClass("btn btn-danger w-100 me-2");
});