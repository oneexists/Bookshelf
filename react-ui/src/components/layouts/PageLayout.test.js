import { render, screen } from "@testing-library/react";
import PageLayout from "./PageLayout";

test("displays title component with text", () => {
    render(<PageLayout pageTitle="Page Title" />);

    const heading = screen.getByRole("heading");

    expect(heading).toBeInTheDocument();
    expect(heading).toHaveTextContent("Page Title");
});

test("displays child component", () => {
    render(
        <PageLayout>
            <p>Child Component</p>
        </PageLayout>
    );

    const childParagraph = screen.getByText(/child component/i);

    expect(childParagraph).toBeInTheDocument();
});