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

function App() {
    const auth = useAuth();

    useEffect(() => {
        refresh().then(auth.login).catch(auth.logout);
    }, []);

    return (
        <div className="app">
            <Header />

            <Routes>
                {(auth.user)
                ? <Route path="/">
                    <Route index element={<Bookshelf />} />
                  </Route>
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
