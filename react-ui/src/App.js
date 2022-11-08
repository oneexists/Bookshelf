import { Routes, Route } from "react-router-dom";
import Footer from "./components/Footer";
import NavBar from "./components/NavBar";
import Home from "./components/Home";
import Header from "./components/Header";

function App() {
  return (
    <div className="app">
      <Header />
      <NavBar />
      
      <Routes>
        <Route path="/" element={<><Home /></>} />
      </Routes>

      <Footer />
    </div>
  );
}

export default App;
