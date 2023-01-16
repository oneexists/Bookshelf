import { Routes, Route } from "react-router-dom";
import Footer from "./components/Footer";
import Header from "./components/Header";
import Home from "./features/Home";
import Login from "./features/Login";
import NotFound from "./features/NotFound";
import Register from "./features/Register";
import { useAuth } from "./hooks/useAuth";
import { useEffect } from "react";
import { refresh } from "./services/authService";
import AllBooks from "./features/Bookshelf/components/AllBooks";
import BookView from "./features/Bookshelf/components/BookView";
import BookAdd from "./features/Bookshelf/components/BookAdd";
import BookEdit from "./features/Bookshelf/components/BookEdit";
import ReadingLogAdd from "./features/Bookshelf/components/ReadingLogAdd";
import ReadingLogEdit from "./features/Bookshelf/components/ReadingLogEdit";
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
                    <Route index element={<AllBooks />} />
                  </Route>
                  
                  <Route path="books">
                    <Route path="add" element={<BookAdd />} />
                    <Route path="unfinished" element={<NotFound />} />
                    <Route path="in-progress" element={<NotFound />} />
                    <Route path="finished" element={<NotFound />} />

                    <Route path=":id">
                        <Route index element={<BookView />} />
                        <Route path="edit" element={<BookEdit />} />

                        <Route path="logs">
                            <Route path="add" element={<ReadingLogAdd />} />
                            <Route path="edit/:logId" element={<ReadingLogEdit />} />
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
