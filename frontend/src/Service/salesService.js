import axios from 'axios';

const BASE_URL = 'http://localhost:8081';

export const getAllSales = () => {
    return axios.get(`${BASE_URL}/sales/all`);
};

export const searchSales = (salesId) => {
    return axios.get(`${BASE_URL}/sales/search/${salesId}`);
};

export const addSales = (sales) => {
    return axios.post(`${BASE_URL}/sales/add`, sales);
};

export const updateSales = (salesId, sales) => {
    return axios.put(`${BASE_URL}/sales/update/${salesId}`, sales);
};

export const deleteSales = (salesId) => {
    return axios.delete(`${BASE_URL}/sales/delete/${salesId}`);
};

export const getAllProducts = () => {
    return axios.get(`${BASE_URL}/product/all`);
};

// New Payment endpoints
export const getAllPayments = () => {
    return axios.get(`${BASE_URL}/payment/all`);
};

export const addPayment = (payment) => {
    return axios.post(`${BASE_URL}/payment/add`, payment);
};

export const updatePayment = (paymentId, payment) => {
    return axios.put(`${BASE_URL}/payment/update/${paymentId}`, payment);
};

export const deletePayment = (paymentId) => {
    return axios.delete(`${BASE_URL}/payment/delete/${paymentId}`);
};
