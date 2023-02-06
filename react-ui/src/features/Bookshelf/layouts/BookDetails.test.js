import { render, screen } from '@testing-library/react';
import BookDetails from './BookDetails';

test("BookDetails displays details", () => {
    render(<BookDetails 
        author={{ id: 3, name: "Stephen King" }} 
        language="English" 
        pages={356} />);

    const author = screen.getByText(/by: /i);
    const language = screen.getByText(/language: /i);
    const pages = screen.getByText(/pages: /i);

    expect(author).toHaveTextContent("Stephen King");
    expect(language).toHaveTextContent("English");
    expect(pages).toHaveTextContent("356");
});