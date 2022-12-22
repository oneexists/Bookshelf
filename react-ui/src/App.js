import { Routes, Route } from "react-router-dom";
import Footer from "./components/Footer";
import Header from "./components/Header";
import Home from "./components/Home";
import Login from "./components/Login";
import NotFound from "./components/NotFound";
import Register from "./components/Register";
import { useAuth } from "./hooks/useAuth";
import { useEffect } from "react";
import { refresh } from "./services/authService";
import Bookshelf from "./components/Bookshelf";
import BookView from "./components/Bookshelf/BookView";
import BookAdd from "./components/Bookshelf/book/BookAdd";
import BookEdit from "./components/Bookshelf/BookEdit";
import ReadingLogAdd from "./components/ReadingLog/ReadingLogAdd";

function App() {
    const auth = useAuth();

    useEffect(() => {
        refresh().then(auth.login).catch(auth.logout);
        // eslint-disable-next-line
    }, []);

    return (
        <div className="app">
            <Header />

            <Routes>
                {(auth.user)
                ? <>
                  <Route path="/">
                    <Route index element={<Bookshelf />} />
                  </Route>
                  
                  <Route path="books">
                    <Route path="add" element={<BookAdd />} />

                    <Route path=":id">
                        <Route index element={<BookView />} />
                        <Route path="edit" element={<BookEdit />} />

                        <Route path="logs">
                            <Route path="add" element={<ReadingLogAdd />} />
                        </Route>
                    </Route>
                  </Route>
                  </>
                : <Route path="/" element={<Home />} />
                }
                <Route path="login" element={<Login />} />
                <Route path="register" element={<Register />} />
                <Route path="*" element={<NotFound />} />
            </Routes>

            <Footer />
        </div>
    );
}

export default App;
