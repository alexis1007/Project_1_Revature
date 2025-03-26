import React, { useEffect, useState } from 'react';
import { Loan } from '../models/Loan';
import {
  getLoans,
  addLoan,
  updateLoan,
  deleteLoan,
  approveLoan,
  rejectLoan,
} from '../services/loanService';
import { logoutUser } from '../services/authService';
import { useNavigate } from 'react-router-dom';

export const Loans = () => {
  const [loans, setLoans] = useState<Loan[]>([]);
  const [formData, setFormData] = useState<Partial<Loan>>({
    principalBalance: 0,
    interest: 0,
    termLength: 0,
    applicationStatus: { id: 1, status: 'pending' },
    loanType: { id: 1, loanType: 'Home' },
  });
  const [editingId, setEditingId] = useState<number | null>(null);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchLoans();
  }, []);

  const fetchLoans = async () => {
    try {
      const data = await getLoans();
      setLoans(data);
    } catch (err) {
      setError('Failed to fetch loans');
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (editingId) {
        await updateLoan(editingId, formData as Loan);
      } else {
        await addLoan(formData as Loan);
      }
      setEditingId(null);
      setFormData({
        principalBalance: 0,
        interest: 0,
        termLength: 0,
        applicationStatus: { id: 1, status: 'pending' },
        loanType: { id: 1, loanType: 'Home' },
      });
      await fetchLoans();
    } catch (err) {
      setError('Failed to save loan');
    }
  };

  const handleDelete = async (id: number) => {
    try {
      await deleteLoan(id);
      await fetchLoans();
    } catch (err) {
      setError('Failed to delete loan');
    }
  };

  const handleStatusChange = async (
    id: number,
    status: 'approved' | 'rejected'
  ) => {
    try {
      const loan = loans.find((l) => l.id === id);
      if (!loan) return;

      if (status === 'approved') {
        await approveLoan(id, loan);
      } else {
        await rejectLoan(id, loan);
      }
      await fetchLoans();
    } catch (err) {
      setError(`Failed to ${status} loan`);
    }
  };

  const startEdit = (loan: Loan) => {
    setEditingId(loan.id!);
    setFormData(loan);
  };

  return (
    <div className="main-container entity-container">
      <div className="header">
        <h2>Loan Operations</h2>
        <button className='logout-button' onClick={() => logoutUser().then(() => navigate('/'))}>
          Logout
        </button>
      </div>

      <div className="main-content-grid">
        <div className="form-column">
          <form onSubmit={handleSubmit} className="entity-form">
            <h3>{editingId ? 'Edit Loan' : 'New Loan'}</h3>

            <div className="form-group">
              <label>Amount:</label>
              <input
                type="number"
                value={formData.principalBalance}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    principalBalance: Number(e.target.value),
                  })
                }
                required
              />
            </div>

            <div className="form-group">
              <label>Interest Rate:</label>
              <input
                type="number"
                step="0.01"
                value={formData.interest}
                onChange={(e) =>
                  setFormData({ ...formData, interest: Number(e.target.value) })
                }
                required
              />
            </div>

            <div className="form-group">
              <label>Term (months):</label>
              <input
                type="number"
                value={formData.termLength}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    termLength: Number(e.target.value),
                  })
                }
                required
              />
            </div>

            <div className="form-group">
              <label>Type:</label>
              <label htmlFor="loanType">Loan Type:</label>
              <select
                id="loanType"
                value={formData.loanType?.id}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    loanType: {
                      id: Number(e.target.value),
                      loanType: e.target.selectedOptions[0].text,
                    },
                  })
                }
              >
                <option value={1}>Home</option>
                <option value={2}>Personal</option>
                <option value={3}>Auto</option>
              </select>
            </div>

            <button type="submit">{editingId ? 'Update' : 'Create'}</button>
            {editingId && (
              <button type="button" onClick={() => setEditingId(null)}>
                Cancel
              </button>
            )}
          </form>
        </div>

        <div className="loans-column">
          <h3>Existing Loans</h3>
          <h4 className="loans-count">Total Loans: {loans.length}</h4>
          <div className="entity-list">
            {error && <div className="error">{error}</div>}

            {loans.map((loan) => (
              <div key={loan.id} className="entity-card">
                <div className="entity-info">
                  <h4>{loan.loanType.loanType} Loan</h4>
                  <h5>${loan.principalBalance}</h5>
                  <p>Interest: {loan.interest}%</p>
                  <p>Term: {loan.termLength} months</p>
                  <p>
                    Status:{' '}
                    <span className={loan.applicationStatus.status}>
                      {loan.applicationStatus.status}
                    </span>
                  </p>
                </div>
                <div className="entity-actions">
                  <button
                    className="edit-button"
                    onClick={() => startEdit(loan)}
                  >
                    Edit
                  </button>
                  <button
                    className="delete-button"
                    onClick={() => handleDelete(loan.id!)}
                  >
                    Delete
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};
