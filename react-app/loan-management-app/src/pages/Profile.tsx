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
    user: { username: 'default', passwordHash: 'defaultpass' },
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
      if (user.userType.id == 1) {
        const data = await getUsersProfiles();
        setUserProfiles(data);
      } else {
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

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (editingId) {
        await updateUserProfile(editingId, formData as UserProfile);
      }
      setEditingId(null);
      setFormData({
        user: { username: 'default', passwordHash: 'defaultpass' },
      });
      await fetchUserProfiles();
    } catch (err) {
      setError('Failed to save user profile');
    }
  };

  return (
    <div className="main-container entity-container">
      <div className="header">
        <h2>User Profile Management</h2>
        <div className="header">
          <button
            className="logout-button"
            onClick={() => logoutUser().then(() => navigate('/'))}
          >
            Logout
          </button>
        </div>
      </div>

      {editingId && (
        <form onSubmit={handleSubmit} className="entity-form">
          <h3>Edit Profile</h3>

          <div className="form-group">
            <label>First Name:</label>
            <input
              type="text"
              value={formData.firstName || ''}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  firstName: e.target.value,
                })
              }
            />
          </div>

          <div className="form-group">
            <label>Last Name:</label>
            <input
              type="text"
              value={formData.lastName || ''}
              onChange={(e) =>
                setFormData({ ...formData, lastName: e.target.value })
              }
            />
          </div>

          <div className="form-group">
            <label>Phone Number:</label>
            <input
              type="text"
              value={formData.phoneNumber || ''}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  phoneNumber: e.target.value,
                })
              }
            />
          </div>

          <div className="form-group">
            <label>Credit Score:</label>
            <input
              type="number"
              value={formData.creditScore || ''}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  creditScore: Number(e.target.value),
                })
              }
            />
          </div>

          <button type="submit">Update</button>
          <button type="button" onClick={() => setEditingId(null)}>
            Cancel
          </button>
        </form>
      )}

      <div className="entity-list">
        {error && <div className="error">{error}</div>}

        {userProfiles.map((userProfile) => (
          <div key={userProfile.id} className="entity-card">
            <div className="entity-info">
              <h4>
                {userProfile.firstName} {userProfile.lastName}
              </h4>
              <p>Phone number: {userProfile.phoneNumber}</p>
              <p>Credit score: {userProfile.creditScore}</p>
            </div>
            <div className="entity-actions">
              <button
                className="edit-button"
                onClick={() => startEdit(userProfile)}
              >
                Edit Profile
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
