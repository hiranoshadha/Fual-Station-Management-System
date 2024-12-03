import axios from 'axios';

const BASE_URL = 'http://localhost:8081';

export const getAllSuppliers = () => {
    return axios.get(`${BASE_URL}/supplier/all`);
};

export const getAllProducts = () => {
    return axios.get(`${BASE_URL}/product/all`);
};

export const addSupplier = (supplier) => {
    return axios.post(`${BASE_URL}/supplier/add`, supplier);
};

export const updateSupplier = (supplierId, supplier) => {
    return axios.put(`${BASE_URL}/supplier/update/${supplierId}`, supplier);
};

export const deleteSupplier = (supplierId) => {
    return axios.delete(`${BASE_URL}/supplier/delete/${supplierId}`);
};
