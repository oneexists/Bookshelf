import { Routes, Route } from "react-router-dom";
import Footer from "./components/Footer";
import Home from "./components/Home";
import Header from "./components/Header";
import Login from "./components/Login";
import Register from "./components/Register";
import Bookshelf from "./components/Bookshelf";
import Book from "./components/Bookshelf/Book";
import NotFound from "./components/NotFound";

function App() {
  return (
    <div className="app">
      <Header />
      
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="login" element={<Login />} />
        <Route path="register" element={<Register />} />
        <Route path="bookshelf" element={<Bookshelf />}>
          <Route path=":id" element={<Book />} />
          <Route index element={<h3>Select a book to view its details</h3>} />
        </Route>
        <Route path="*" element={<NotFound />} />
      </Routes>

      <Footer />
    </div>
  );
}

export default App;
