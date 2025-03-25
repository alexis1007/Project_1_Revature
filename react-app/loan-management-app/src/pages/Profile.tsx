import React, { useEffect, useState } from 'react';
import { UserProfile } from '../models/UserProfile';
import {
  getUsersProfiles,
  getUsersProfileById,
  updateUserProfile,
} from '../services/profileService';
import { logoutUser } from '../services/authService';
import { useNavigate } from 'react-router-dom';

const getUser = () => localStorage.getItem('user');

export const UserProfiles = () => {
  const [userProfiles, setUserProfiles] = useState<UserProfile[]>([]);
  const [formData, setFormData] = useState<Partial<UserProfile>>({
    user: {username: "default", passwordHash: "defaultpass"},
    // mailingAddress?: { id: 1},
    // firstName?: string;
    // lastName?: string;
    // phoneNumber?: string;
    // creditScore?: number;
    // birthDate?: string;
  });
  const [editingId, setEditingId] = useState<number | null>(null);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchUserProfiles();
  }, []);

  const fetchUserProfiles = async () => {
    try {
      const user = JSON.parse(getUser() || '{}');
      if(user.userType.id == 1) { 
        const data = await getUsersProfiles();
        setUserProfiles(data);
    }
      else {
        const data = await getUsersProfileById(user.id);
        setUserProfiles([data]);
    }
      
    } catch (err) {
      setError('Failed to fetch user profiles');
    }
  };

  const startEdit = (userProfile: UserProfile) => {
    setEditingId(userProfile.id!);
    setFormData(userProfile);
  };

  return (
    <div className="main-container user-profiles-container">
      <div className="header">
        <h2>User Profile Management</h2>
        <button onClick={() => logoutUser().then(() => navigate('/'))}>
          Logout
        </button>
      </div>

      {/* <form onSubmit={handleSubmit} className="loan-form">
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
              setFormData({ ...formData, termLength: Number(e.target.value) })
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
      </form> */}

      <div className="user-profiles-list">
        {error && <div className="error">{error}</div>}

        {userProfiles.map((userProfile) => (
          <div key={userProfile.id} className="user-profile-card">
            <div className="user-profile-info">
              <h4>{userProfile.firstName}</h4>
              <h5>{userProfile.lastName}</h5>
              <p>Phone number: {userProfile.phoneNumber}%</p>
              <p>Credit score: {userProfile.creditScore} months</p>
              {/* <p>
                Status:{' '}
                <span className={loan.applicationStatus.status}>
                  {loan.applicationStatus.status}
                </span>
              </p> */}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
