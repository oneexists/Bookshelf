import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import SubmitPanel from "./SubmitPanel";

test("submit panel displays default text", () => {
    render(
        <MemoryRouter>
            <SubmitPanel />
        </MemoryRouter>
    );

    const submitButton = screen.getByText("Submit");

    expect(submitButton).toBeInTheDocument();
});

test("submit panel displays text", () => {
    render(
        <MemoryRouter>
            <SubmitPanel text="Submit Button" />
        </MemoryRouter>
    );

    const submitButton = screen.getByText("Submit Button");

    expect(submitButton).toBeInTheDocument();
});