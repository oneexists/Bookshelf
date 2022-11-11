import { Routes, Route } from "react-router-dom";
import { createContext } from "react";
import Footer from "./components/Footer";
import Header from "./components/Header";
import Home from "./components/Home";
import NotFound from "./components/NotFound";
import Register from "./components/Register";

export const TreesContext = createContext();

const trees = [
    { id: 1, type: "Maple" },
    { id: 2, type: "Oak" },
    { id: 3, type: "Family" },
    { id: 4, type: "Maple" }
];

function App() {  
  return (
    <TreesContext.Provider value={{trees}}>
      <div className="app">
        <Header />

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="register" element={<Register />} />
          <Route path="*" element={<NotFound />} />
        </Routes>

        <Footer />
      </div>
    </TreesContext.Provider>
  );
}

export default App;
