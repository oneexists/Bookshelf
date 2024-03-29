# Requirements

## Functional

- RESTful API with JWT web token authentication
  - Login/Logout and account registration
  - Username length between 3 and 100 characters
  - Password validation: 8 character length with at least one letter, one digit
    and one special character
- Add, edit and delete books
- Add, edit and delete a book's reading activity
- Filter user books by unfinished, in progress, and finished status

## Non-Functional

### User Interface

- React front-end interface
- Bootstrap/CSS module styling
- Utilize a card layout for books on bookshelf
- Drop-down menu to display 'bookshelves' based on reading status (all, unread, currently reading, read)

### User Experience

- Integrate [WAI-ARIA specification](https://developer.mozilla.org/en-US/docs/Learn/Accessibility/WAI-ARIA_basics)
  to increase accessibility
  - Allows for screen reader and keyboard navigation
  - Semantic HTML elements
  - Assertive error display on forms to notify screen readers
  - Descriptions of form field requirements
- Standardize custom components such as form input, submission and button formatting

## Class Diagram

![Bookshelf Class Diagram](./bookshelf-class-diagram.drawio.png)
