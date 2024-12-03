import React, { useState, useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';
import { getAllSuppliers, addSupplier, updateSupplier, deleteSupplier, getAllProducts } from '../Service/supplierService';

const SupplierAdd = () => {
  const [formData, setFormData] = useState({
    name: '',
    mobile: '',
    products: []
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

  const validateMobile = (mobile) => {
    // Sri Lankan mobile number format validation
    const mobileRegex = /^(?:\+94|0)?[0-9]{9}$/;
    return mobileRegex.test(mobile);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateMobile(formData.mobile)) {
      alert('Please enter a valid mobile number');
      return;
    }
    try {
      await addSupplier(formData);
      alert('Supplier added successfully');
      setFormData({ name: '', mobile: '', products: [] });
      e.target.querySelector('select').selectedIndex = -1;
    } catch (error) {
      alert('Error adding supplier');
    }
  };

  const handleProductSelection = (e) => {
    const selectedProducts = Array.from(e.target.selectedOptions, option => ({
      productId: parseInt(option.value)
    }));
    setFormData({...formData, products: selectedProducts});
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-2xl font-bold mb-4">Add New Supplier</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2">Name</label>
          <input
            type="text"
            className="w-full px-3 py-2 border rounded-lg"
            value={formData.name}
            onChange={(e) => setFormData({...formData, name: e.target.value})}
            required
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2">Mobile</label>
          <input
            type="tel"
            className="w-full px-3 py-2 border rounded-lg"
            value={formData.mobile}
            onChange={(e) => setFormData({...formData, mobile: e.target.value})}
            required
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2">Products</label>
          <select
            multiple
            className="w-full px-3 py-2 border rounded-lg"
            onChange={handleProductSelection}
            required
          >
            {products.map(product => (
              <option key={product.productId} value={product.productId}>
                {product.product} - Rs.{product.unitPrice}
              </option>
            ))}
          </select>
        </div>
        <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded">
          Add Supplier
        </button>
      </form>
    </div>
  );
};

const SupplyManage = () => {
  const [suppliers, setSuppliers] = useState([]);
  const [editingSupplier, setEditingSupplier] = useState(null);
  const [products, setProducts] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    loadSuppliers();
    loadProducts();
  }, []);

  const loadSuppliers = async () => {
    try {
      const response = await getAllSuppliers();
      setSuppliers(response.data);
    } catch (error) {
      alert('Error loading suppliers');
    }
  };

  const loadProducts = async () => {
    try {
      const response = await getAllProducts();
      setProducts(response.data);
    } catch (error) {
      alert('Error loading products');
    }
  };

  const validateMobile = (mobile) => {
    // Sri Lankan mobile number format validation
    const mobileRegex = /^(?:\+94|0)?[0-9]{9}$/;
    return mobileRegex.test(mobile);
  };
  
  const handleUpdate = async (supplier) => {
    if (!validateMobile(supplier.mobile)) {
      alert('Please enter a valid mobile number');
      return;
    }
    try {
      await updateSupplier(supplier.supplierId, supplier);
      setEditingSupplier(null);
      loadSuppliers();
      alert('Supplier updated successfully');
    } catch (error) {
      alert('Error updating supplier');
    }
  };

  const handleDelete = async (supplierId) => {
    if (window.confirm('Are you sure you want to delete this supplier?')) {
      try {
        await deleteSupplier(supplierId);
        loadSuppliers();
        alert('Supplier deleted successfully');
      } catch (error) {
        alert('Error deleting supplier');
      }
    }
  };

  const filteredSuppliers = suppliers.filter(supplier => 
    supplier.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    supplier.mobile.includes(searchTerm) ||
    supplier.products.some(p => p.product.toLowerCase().includes(searchTerm.toLowerCase()))
  );

  return (
    <div className="bg-white p-6 rounded-lg shadow">
      <h2 className="text-2xl font-bold mb-4">Manage Suppliers</h2>
      
      <div className="mb-4">
        <input
          type="text"
          placeholder="Search by name, mobile or products..."
          className="w-full px-3 py-2 border rounded-lg"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      <table className="w-full">
        <thead>
          <tr className="bg-gray-100">
            <th className="p-2">ID</th>
            <th className="p-2">Name</th>
            <th className="p-2">Mobile</th>
            <th className="p-2">Products</th>
            <th className="p-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredSuppliers.map((supplier) => (
            <tr key={supplier.supplierId} className="border-b">
              <td className="p-2">{supplier.supplierId}</td>
              <td className="p-2">
                {editingSupplier?.supplierId === supplier.supplierId ? (
                  <input
                    type="text"
                    value={editingSupplier.name}
                    onChange={(e) => setEditingSupplier({...editingSupplier, name: e.target.value})}
                    className="border rounded px-2 py-1"
                  />
                ) : (
                  supplier.name
                )}
              </td>
              <td className="p-2">
                {editingSupplier?.supplierId === supplier.supplierId ? (
                  <input
                    type="text"
                    value={editingSupplier.mobile}
                    onChange={(e) => setEditingSupplier({...editingSupplier, mobile: e.target.value})}
                    className="border rounded px-2 py-1"
                  />
                ) : (
                  supplier.mobile
                )}
              </td>
              <td className="p-2">
                {editingSupplier?.supplierId === supplier.supplierId ? (
                  <select
                    multiple
                    className="border rounded px-2 py-1"
                    value={editingSupplier.products.map(p => p.productId)}
                    onChange={(e) => {
                      const selectedProducts = Array.from(e.target.selectedOptions, option => ({
                        productId: parseInt(option.value)
                      }));
                      setEditingSupplier({...editingSupplier, products: selectedProducts});
                    }}
                  >
                    {products.map(product => (
                      <option key={product.productId} value={product.productId}>
                        {product.product}
                      </option>
                    ))}
                  </select>
                ) : (
                  supplier.products.map(p => p.product).join(', ')
                )}
              </td>
              <td className="p-2">
                {editingSupplier?.supplierId === supplier.supplierId ? (
                  <div>
                    <button
                      onClick={() => handleUpdate(editingSupplier)}
                      className="bg-green-500 text-white px-2 py-1 rounded mr-2"
                    >
                      Save
                    </button>
                    <button
                      onClick={() => setEditingSupplier(null)}
                      className="bg-gray-500 text-white px-2 py-1 rounded"
                    >
                      Cancel
                    </button>
                  </div>
                ) : (
                  <div>
                    <button
                      onClick={() => setEditingSupplier(supplier)}
                      className="bg-blue-500 text-white px-2 py-1 rounded mr-2"
                    >
                      Edit
                    </button>
                    <button
                      onClick={() => handleDelete(supplier.supplierId)}
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
const SupplierM = () => {
  return (
    <Routes>
      <Route path="add" element={<SupplierAdd />} />
      <Route path="manage" element={<SupplyManage />} />
    </Routes>
  );
};

export default SupplierM;
