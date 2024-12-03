import React, { useState, useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';
import { getAllSales, addSales, updateSales, deleteSales, getAllProducts, getAllPayments, addPayment } from '../Service/salesService';

const SalesAdd = () => {
  const [formData, setFormData] = useState({
    machine: '',
    product: null,
    qty: ''
  });
  const [products, setProducts] = useState([]);

  useEffect(() => {
    loadProducts();
  }, []);

  const loadProducts = async () => {
    try {
      const response = await getAllProducts();
      setProducts(response.data);
    } catch (error) {
      alert('Error loading products');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await addSales(formData);
      alert('Sales added successfully');
      setFormData({ machine: '', product: null, qty: '' });
      e.target.reset();
      // Reset the select element to its default option
      const selectElement = e.target.querySelector('select');
      if (selectElement) {
        selectElement.value = '';
      }
      window.location.reload();
    } catch (error) {
      alert('Error adding sales');
    }
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-2xl font-bold mb-4">Add New Sales</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2">Product</label>
          <select
            className="w-full px-3 py-2 border rounded-lg"
            onChange={(e) => {
              const selectedProduct = products.find(p => p.productId === parseInt(e.target.value));
              setFormData({
                ...formData,
                product: selectedProduct,
                machine: selectedProduct?.product || ''
              });
            }}
            required
          >
            <option value="">Select Product</option>
            {products.map(product => (
              <option key={product.productId} value={product.productId}>
                {product.product} - Rs.{product.unitPrice}
              </option>
            ))}
          </select>
        </div>

        {formData.product && (
          <>
            <div className="mb-4">
              <label className="block text-gray-700 text-sm font-bold mb-2">Machine Name</label>
              <input
                type="text"
                className="w-full px-3 py-2 border rounded-lg"
                value={formData.machine}
                readOnly
              />
            </div>

            <div className="mb-4">
              <label className="block text-gray-700 text-sm font-bold mb-2">Quantity (Liters)</label>
              <input
                type="number"
                className="w-full px-3 py-2 border rounded-lg"
                value={formData.qty}
                onChange={(e) => setFormData({...formData, qty: parseFloat(e.target.value)})}
                min="0"
                step="0.01"
                required
              />
            </div>
          </>
        )}

        <button 
          type="submit" 
          className="bg-blue-600 text-white px-4 py-2 rounded"
          disabled={!formData.product || !formData.qty}
        >
          Add Sales
        </button>
      </form>
    </div>
  );
};

const SalesManage = () => {
  const [sales, setSales] = useState([]);
  const [editingSale, setEditingSale] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    loadSales();
  }, []);

  const loadSales = async () => {
    try {
      const response = await getAllSales();
      setSales(response.data);
    } catch (error) {
      alert('Error loading sales');
    }
  };

  const handleUpdate = async (sale) => {
    try {
      const updateData = {
        salesId: sale.salesId,
        machine: sale.machine,
        product: sale.product,
        qty: parseFloat(sale.qty),
        date: sale.date
      };
      
      await updateSales(sale.salesId, updateData);
      await loadSales(); // Reload the data immediately after update
      setEditingSale(null);
      alert('Sales updated successfully');
    } catch (error) {
      alert('Error updating sales: ' + error.message);
    }
  };

  const handleDelete = async (salesId) => {
    if (window.confirm('Are you sure you want to delete this sales record?')) {
      try {
        await deleteSales(salesId);
        loadSales();
        alert('Sales deleted successfully');
      } catch (error) {
        alert('Error deleting sales');
      }
    }
  };

  const filteredSales = sales.filter(sale => 
    (sale.machine?.toLowerCase() || '').includes(searchTerm.toLowerCase()) ||
    (sale.product?.product?.toLowerCase() || '').includes(searchTerm.toLowerCase()) ||
    (sale.qty?.toString() || '').includes(searchTerm)
  );

  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-2xl font-bold mb-4">Manage Sales</h2>
      
      <div className="mb-4">
        <input
          type="text"
          placeholder="Search by machine, product or quantity..."
          className="w-full px-3 py-2 border rounded-lg"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      <table className="w-full">
        <thead>
          <tr className="bg-gray-100">
            <th className="p-2">ID</th>
            <th className="p-2">Machine</th>
            <th className="p-2">Product</th>
            <th className="p-2">Quantity (L)</th>
            <th className="p-2">Date</th>
            <th className="p-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredSales.map((sale) => (
            <tr key={sale.salesId} className="border-b">
              <td className="p-2">{sale.salesId}</td>
              <td className="p-2">{sale.machine || 'N/A'}</td>
              <td className="p-2">{sale.product?.product || 'N/A'}</td>
              <td className="p-2">
                {editingSale?.salesId === sale.salesId ? (
                  <input
                    type="number"
                    value={editingSale.qty}
                    onChange={(e) => setEditingSale({...editingSale, qty: parseFloat(e.target.value)})}
                    className="border rounded px-2 py-1"
                    min="0"
                    step="0.01"
                  />
                ) : (
                  sale.qty || 0
                )}
              </td>
              <td className="p-2">{new Date(sale.date).toLocaleDateString()}</td>
              <td className="p-2">
                {editingSale?.salesId === sale.salesId ? (
                  <div>
                    <button
                      onClick={() => handleUpdate(editingSale)}
                      className="bg-green-500 text-white px-2 py-1 rounded mr-2"
                    >
                      Save
                    </button>
                    <button
                      onClick={() => setEditingSale(null)}
                      className="bg-gray-500 text-white px-2 py-1 rounded"
                    >
                      Cancel
                    </button>
                  </div>
                ) : (
                  <div>
                    <button
                      onClick={() => setEditingSale(sale)}
                      className="bg-blue-500 text-white px-2 py-1 rounded mr-2"
                    >
                      Edit
                    </button>
                    <button
                      onClick={() => handleDelete(sale.salesId)}
                      className="bg-red-500 text-white px-2 py-1 rounded"
                    >
                      Delete
                    </button>
                  </div>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );

  
};

const Payments = () => {
  const [payments, setPayments] = useState([]);
  const [formData, setFormData] = useState({
    nic: '',
    amount: ''
  });
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    loadPayments();
  }, []);

  const loadPayments = async () => {
    try {
      const response = await getAllPayments();
      console.log(response.data)
      setPayments(response.data);
    } catch (error) {
      alert('Error loading payments');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await addPayment(formData);
      alert('Payment added successfully');
      setFormData({ nic: '', amount: '' });
      loadPayments();
    } catch (error) {
      alert('Error adding payment');
    }
  };

  const filteredPayments = payments.filter(payment => 
    payment.customer?.nic.toLowerCase().includes(searchTerm.toLowerCase()) ||
    payment.amount.toString().includes(searchTerm)
  );

  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-2xl font-bold mb-4">Payments</h2>
      
      {/* Payment Form */}
      <form onSubmit={handleSubmit} className="mb-8">
        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2">Customer NIC</label>
          <input
            type="text"
            className="w-full px-3 py-2 border rounded-lg"
            value={formData.nic}
            onChange={(e) => setFormData({...formData, nic: e.target.value})}
            required
          />
        </div>

        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2">Amount</label>
          <input
            type="number"
            className="w-full px-3 py-2 border rounded-lg"
            value={formData.amount}
            onChange={(e) => setFormData({...formData, amount: parseFloat(e.target.value)})}
            min="0"
            step="0.01"
            required
          />
        </div>

        <button 
          type="submit" 
          className="bg-blue-600 text-white px-4 py-2 rounded"
          disabled={!formData.nic || !formData.amount}
        >
          Add Payment
        </button>
      </form>

      {/* Payments Table */}
      <div className="mb-4">
        <input
          type="text"
          placeholder="Search by NIC or amount..."
          className="w-full px-3 py-2 border rounded-lg"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      <table className="w-full">
        <thead>
          <tr className="bg-gray-100">
            <th className="p-2">ID</th>
            <th className="p-2">Customer NIC</th>
            <th className="p-2">Amount</th>
            <th className="p-2">Date</th>
          </tr>
        </thead>
        <tbody>
          {filteredPayments.map((payment) => (
            <tr key={payment.paymentId} className="border-b">
              <td className="p-2">{payment.paymentId}</td>
              <td className="p-2">{payment?.nic || 'N/A'}</td>
              <td className="p-2">Rs. {payment.amount.toFixed(2)}</td>
              <td className="p-2">{new Date(payment.date).toLocaleDateString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

const SalesM = () => {
  return (
    <Routes>
      <Route path="add" element={<SalesAdd />} />
      <Route path="manage" element={<SalesManage />} />
      <Route path="payments" element={<Payments />} />
      
    </Routes>
  );
};

export default SalesM;
