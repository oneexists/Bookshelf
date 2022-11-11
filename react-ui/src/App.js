import { Routes, Route } from "react-router-dom";
import Footer from "./components/Footer";
import Header from "./components/Header";
import Home from "./components/Home";
import NotFound from "./components/NotFound";
import Register from "./components/Register";
import { ProvideAuth } from "./hooks/useAuth";

function App() {
  return (
    <ProvideAuth>
      <div className="app">
        <Header />

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="register" element={<Register />} />
          <Route path="*" element={<NotFound />} />
        </Routes>

        <Footer />
      </div>
    </ProvideAuth>
  );
}

export default App;
