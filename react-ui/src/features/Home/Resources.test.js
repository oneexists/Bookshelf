import { render, screen } from '@testing-library/react';
import Resources from './Resources';

test("resources displays heading and two resource links", () => {
    render(<Resources />);

    const heading = screen.getByRole("heading");
    const links = screen.getAllByRole("link");

    expect(heading).toBeInTheDocument();
    expect(links).toHaveLength(2);
});