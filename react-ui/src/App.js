import { Routes, Route } from "react-router-dom";
import NotFound from "./components/NotFound";

function App() {
  return (
    <div className="app">
      <Routes>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </div>
  );
}

export default App;
