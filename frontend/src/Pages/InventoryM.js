import React, { useState, useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';
import { getAllInventory, addInventory, updateInventory, deleteInventory, getAllSuppliers, getAllProducts } from '../Service/inventoryService';

const StockAdd = () => {
  const [formData, setFormData] = useState({
    supplier: null,
    product: null,
    qty: ''
  });
  const [suppliers, setSuppliers] = useState([]);
  const [selectedSupplier, setSelectedSupplier] = useState(null);
  const [products, setProducts] = useState([]);

  useEffect(() => {
    loadSuppliers();
  }, []);

  const loadSuppliers = async () => {
    try {
      const response = await getAllSuppliers();
      setSuppliers(response.data);
    } catch (error) {
      alert('Error loading suppliers');
    }
  };

  const handleSupplierChange = (e) => {
    const supplier = suppliers.find(s => s.supplierId === parseInt(e.target.value));
    setSelectedSupplier(supplier);
    setFormData({...formData, supplier: supplier, product: null, qty: ''});
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await addInventory(formData);
      alert('Stock added successfully');
      setFormData({ supplier: null, product: null, qty: '' });
      setSelectedSupplier(null);
    } catch (error) {
      alert('Error adding stock');
    }
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-2xl font-bold mb-4">Add New Stock</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2">Supplier</label>
          <select
            className="w-full px-3 py-2 border rounded-lg"
            onChange={handleSupplierChange}
            required
          >
            <option value="">Select Supplier</option>
            {suppliers.map(supplier => (
              <option key={supplier.supplierId} value={supplier.supplierId}>
                {supplier.name}
              </option>
            ))}
          </select>
        </div>

        {selectedSupplier && (
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-bold mb-2">Product</label>
            <select
              className="w-full px-3 py-2 border rounded-lg"
              onChange={(e) => setFormData({
                ...formData,
                product: selectedSupplier.products.find(p => p.productId === parseInt(e.target.value))
              })}
              required
            >
              <option value="">Select Product</option>
              {selectedSupplier.products.map(product => (
                <option key={product.productId} value={product.productId}>
                  {product.product} - Rs.{product.unitPrice}
                </option>
              ))}
            </select>
          </div>
        )}

        {formData.product && (
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
        )}

        <button 
          type="submit" 
          className="bg-blue-600 text-white px-4 py-2 rounded"
          disabled={!formData.supplier || !formData.product || !formData.qty}
        >
          Add Stock
        </button>
      </form>
    </div>
  );
};

const StockManage = () => {
  const [inventory, setInventory] = useState([]);
  const [editingStock, setEditingStock] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    loadInventory();
  }, []);

  const loadInventory = async () => {
    try {
      const response = await getAllInventory();
      setInventory(response.data);
    } catch (error) {
      alert('Error loading inventory');
    }
  };

  const handleUpdate = async (stock) => {
    try {
      await updateInventory(stock.inventoryId, stock);
      setEditingStock(null);
      loadInventory();
      alert('Stock updated successfully');
    } catch (error) {
      alert('Error updating stock');
    }
  };

  const handleDelete = async (inventoryId) => {
    if (window.confirm('Are you sure you want to delete this stock?')) {
      try {
        await deleteInventory(inventoryId);
        loadInventory();
        alert('Stock deleted successfully');
      } catch (error) {
        alert('Error deleting stock');
      }
    }
  };

  const filteredInventory = inventory.filter(stock => 
    (stock.supplier?.name || '').toLowerCase().includes(searchTerm.toLowerCase()) ||
    (stock.product?.product || '').toLowerCase().includes(searchTerm.toLowerCase()) ||
    (stock.qty?.toString() || '').includes(searchTerm)
  );
  

  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-2xl font-bold mb-4">Manage Stock</h2>
      
      <div className="mb-4">
        <input
          type="text"
          placeholder="Search by supplier, product or quantity..."
          className="w-full px-3 py-2 border rounded-lg"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      <table className="w-full">
        <thead>
          <tr className="bg-gray-100">
            <th className="p-2">ID</th>
            <th className="p-2">Supplier</th>
            <th className="p-2">Product</th>
            <th className="p-2">Quantity (L)</th>
            <th className="p-2">Actions</th>
          </tr>
        </thead>
        <tbody>
        {filteredInventory.map((stock) => (
  <tr key={stock.inventoryId} className="border-b">
    <td className="p-2">{stock.inventoryId}</td>
    <td className="p-2">{stock.supplier?.name || 'N/A'}</td>
    <td className="p-2">{stock.product?.product || 'N/A'}</td>
    <td className="p-2">
      {editingStock?.inventoryId === stock.inventoryId ? (
        <input
          type="number"
          value={editingStock.qty}
          onChange={(e) => setEditingStock({...editingStock, qty: parseFloat(e.target.value)})}
          className="border rounded px-2 py-1"
          min="0"
          step="0.01"
        />
      ) : (
        stock.qty || 0
      )}
    </td>
              <td className="p-2">
                {editingStock?.inventoryId === stock.inventoryId ? (
                  <div>
                    <button
                      onClick={() => handleUpdate(editingStock)}
                      className="bg-green-500 text-white px-2 py-1 rounded mr-2"
                    >
                      Save
                    </button>
                    <button
                      onClick={() => setEditingStock(null)}
                      className="bg-gray-500 text-white px-2 py-1 rounded"
                    >
                      Cancel
                    </button>
                  </div>
                ) : (
                  <div>
                    <button
                      onClick={() => setEditingStock(stock)}
                      className="bg-blue-500 text-white px-2 py-1 rounded mr-2"
                    >
                      Edit
                    </button>
                    <button
                      onClick={() => handleDelete(stock.inventoryId)}
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
const InventoryM = () => {
  return (
    <Routes>
      <Route path="add" element={<StockAdd />} />
      <Route path="manage" element={<StockManage />} />
    </Routes>
  );
};

export default InventoryM;
