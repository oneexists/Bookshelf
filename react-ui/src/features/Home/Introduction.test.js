import { render, screen } from '@testing-library/react';
import Introduction from './Introduction';

test("introduction displays two paragraphs about reading", () => {
    render(<Introduction />);

    const paragraphs = screen.getAllByText(/reading/);
    expect(paragraphs).toHaveLength(2);
});