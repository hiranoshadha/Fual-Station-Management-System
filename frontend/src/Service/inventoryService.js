import axios from 'axios';

const BASE_URL = 'http://localhost:8081';

export const getAllInventory = () => {
    return axios.get(`${BASE_URL}/inventory/all`);
};

export const getAllSuppliers = () => {
    return axios.get(`${BASE_URL}/supplier/all`);
};

export const getAllProducts = () => {
    return axios.get(`${BASE_URL}/product/all`);
};

export const addInventory = (inventory) => {
    return axios.post(`${BASE_URL}/inventory/add`, inventory);
};

export const updateInventory = (inventoryId, inventory) => {
    return axios.put(`${BASE_URL}/inventory/update/${inventoryId}`, inventory);
};

export const deleteInventory = (inventoryId) => {
    return axios.delete(`${BASE_URL}/inventory/delete/${inventoryId}`);
};
