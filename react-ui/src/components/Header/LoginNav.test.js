import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import LoginNav from "./LoginNav";

test("login navigation displays login and register links", () => {
    render(
        <MemoryRouter>
            <LoginNav />
        </MemoryRouter>
    );

    const loginLink = screen.getByRole("link", { name: "Login" });
    const registerLink = screen.getByRole("link", { name: "Register" });

    expect(loginLink).toBeInTheDocument();
    expect(registerLink).toBeInTheDocument();
});