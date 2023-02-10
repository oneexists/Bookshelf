import { render, screen } from "@testing-library/react";
import ButtonBar from "./ButtonBar";

test("creates div with appropriate classes", () => {
    render(<ButtonBar />);

    const buttonBar = screen.getByTestId('button-bar');

    expect(buttonBar).toBeInTheDocument();
    expect(buttonBar).toHaveClass("d-flex justify-content-center");
});

test("displays child component", () => {
    render(
        <ButtonBar>
            <button></button>
        </ButtonBar>
    );

    const childBtn = screen.getByRole("button");

    expect(childBtn).toBeInTheDocument();
});