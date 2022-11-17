const books = [
    {id: 1, title: "War and Peace", author: "Leo Tolstoy", pages: 1296, language: "English"},
    {id: 2, title: "L'étranger", author: "Albert Camus", pages: 185, language: "French"}
]

export function findAll() {
    return books;
}

export function findById(id) {
    return books.find((book) => book.id === id);
}

export function save(book) {
    console.log(book);
    return book;
}