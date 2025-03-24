import React, { useEffect, useState } from 'react';
import { Loan } from '../models/Loan';
import { addLoan, getLoans } from '../services/loanService';
import { logoutUser } from '../services/authService';
import { useNavigate } from 'react-router-dom';

export const DashboardPage = () => {
  const [loans, setLoans] = useState<Loan[]>([]);
  const [id, setId] = useState('');
  //const [dueDate, setDueDate] = useState('');
  const navigate = useNavigate();

  const fetchLoans = async () => {
    try {
      const res = await getLoans();
      const data = await res.json();
      if (Array.isArray(data)) {
        setLoans(data);
      } else {
        setLoans([data.task]);
      }
    } catch (err) {
      console.error(err);
    }
  };

  const handleAddLoan = async () => {
    const loan: Loan = {
      id: 0, // Provide a default or generated ID
      principal_balance: 0, // Default value
      interest: 0, // Default value
      term_length: 0, // Default value
      total_balance: 0, // Default value
      applicationStatus: { application_statuses_id: 1 },
      loanType: {
        loan_type_id: 0,
      },
      userProfile: {
        user_profiles_id: 0,
      },
    };
    await addLoan(loan);
    setId('');
    //setDueDate('');
    fetchLoans();
  };

  const handleLogout = async () => {
    await logoutUser();
    navigate('/');
  };

  useEffect(() => {
    fetchLoans();
  }, []);

  return (
    <div className="main-container dashboard-container">
      <div className="dashboard-header">
        <h2 className="form-title">Loan Dashboard</h2>
      </div>

      <div className="loan-form-container">
        <h3 className="section-title">Add New Loan</h3>
        <div className="auth-form">
          <div className="form-group">
            <label htmlFor="loan_type_id" className="input-label">
              loan_type_id
            </label>
            <input
              id="loan_type_id"
              value={id}
              onChange={(e) => setId(e.target.value)}
              className="form-input"
              placeholder="Loan Description"
            />
          </div>

          <div className="form-group">
            {/* <label htmlFor="dueDate" className="input-label">
              Due Date
            </label>
            <input
              id="dueDate"
              type="date"
              value={dueDate}
              onChange={(e) => setDueDate(e.target.value)}
              className="form-input date-input"
            /> */}
          </div>

          <button className="submit-button" onClick={handleAddLoan}>
            Add Loan
          </button>
        </div>
      </div>

      <div className="loans-container">
        <h3 className="section-title">Your Loans</h3>
        <ul className="loan-list">
          {loans.map((loan, i) => (
            <li key={i} className="loan-item">
              <div className="loan-content">
                <h4 className="loan-title">loan.id</h4>
                <p className="loan-description">loan.principal_balance</p>
                <p className="loan-description">loan.interest</p>
                <p className="loan-description">loan.term_length</p>
                <p className="loan-description">loan.total_balance</p>
                <p className="loan-description">
                  loan.applicationStatus.application_statuses_id
                </p>

                {/* {loan.interest && (
                  <p className="loan-due-date">
                    Due: {new Date(loan.dueDate).toLocaleDateString()}
                  </p>
                )} */}
              </div>
            </li>
          ))}
        </ul>
      </div>
      <button className="logout-button" onClick={handleLogout}>
        Logout
      </button>
    </div>
  );
};
