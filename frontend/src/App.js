import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import HomePage from './Pages/Homepage';
import Login from './Pages/Login';
import Register from './Pages/Register';
import Dashboard from './Pages/Dashboard';
import InventoryM from './Pages/InventoryM';
import SalesM from './Pages/SalesM';
import SupplierM from './Pages/SupplierM';
import CustomerM from './Pages/CustomerM';
import Navbar from './Components/navbar';
import Footer from './Components/footer';

const App = () => {
  const isAuthenticated = localStorage.getItem('user');
  const userRole = localStorage.getItem('userRole');
  return (
    <Router>
      <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
        <Navbar />
        <main style={{ flex: 1 }}>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={!isAuthenticated ? <Login /> : <Navigate to="/dashboard" />} />
            <Route path="/register" element={!isAuthenticated ? <Register /> : <Navigate to="/dashboard" />} />
            <Route 
              path="/dashboard/*" 
              element={isAuthenticated ? <Dashboard userRole={userRole} /> : <Navigate to="/login" />}
            >
              <Route path="inventory/*" element={<InventoryM />} />
              <Route path="sales/*" element={<SalesM />} />
              <Route path="supplier/*" element={<SupplierM />} />
              <Route path="customer/*" element={<CustomerM />} />
            </Route>
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
  );
};

export default App;