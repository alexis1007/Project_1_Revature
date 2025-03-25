import { Loan } from '../models/Loan';

const API_BASE_URL = 'http://localhost:7070';

const getToken = () => localStorage.getItem('token');

export const getLoans = async () => {
  const token = getToken();
  if (!token) throw new Error('No token found');

  return fetch(`${API_BASE_URL}/api/loans`, {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`,  // Add token here
    },
    credentials: 'include',
  });
};

export const addLoan = async (task: Loan) => {
  return fetch(`${API_BASE_URL}/api/loans`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(task),
    credentials: 'include',
  });
};
