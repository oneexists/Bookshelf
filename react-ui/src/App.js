import { Routes, Route } from "react-router-dom";
import Footer from "./components/Footer";
import NotFound from "./components/NotFound";

function App() {
  return (
    <div className="app">
      <Routes>
        <Route path="*" element={<NotFound />} />
      </Routes>

      <Footer />
    </div>
  );
}

export default App;
