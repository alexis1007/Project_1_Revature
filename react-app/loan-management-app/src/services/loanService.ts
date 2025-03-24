import { Loan } from '../models/Loan';

const API_BASE_URL = 'http://localhost:7070';

export const getLoans = async () => {
  return fetch(`${API_BASE_URL}/api/loans`, {
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
