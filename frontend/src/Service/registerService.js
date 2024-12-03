import axios from 'axios';

const BASE_URL = 'http://localhost:8081';

export const registerCustomer = (customerData) => {
    return axios.post(`${BASE_URL}/customer/save`, customerData);
};
