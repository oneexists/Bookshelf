import { render, screen } from "@testing-library/react";
import SectionLabel from "./SectionLabel";

test("section label displays text and child element", () => {
    render(<SectionLabel text="Section Label" children={<p>Child Text</p>} />);

    const sectionLabel = screen.getByText("Section Label");
    const children = screen.getByText("Child Text");

    expect(sectionLabel).toBeInTheDocument();
    expect(children).toBeInTheDocument();
});