import React, { useState } from 'react';
import { registerUser } from '../services/authService';

export const RegisterPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [roleId, setRoleId] = useState('');
  const [message, setMessage] = useState('');

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await registerUser({
        username: email,
        password,
        role: { roleId: parseInt(roleId) },
      });
      if (!response.ok) throw new Error('Registration failed');
      setMessage('Registration successful!');
    } catch (error: unknown) {
      if (error instanceof Error) {
        setMessage(error.message);
      } else {
        setMessage('Unknown error occurred');
      }
    }
  };

  return (
    <div className="main-container">
      <div className="sm-container">
        <h2 className="form-title">Create an account</h2>
      </div>

      <form className="auth-form" onSubmit={handleRegister}>
        <div className="form-group">
          <label htmlFor="email" className="input-label">
            Username
          </label>
          <input
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="form-input"
            placeholder="Email"
            type="email"
            required
            autoComplete="email"
          />
        </div>

        <div className="form-group">
          <label htmlFor="password" className="input-label">
            Password
          </label>
          <input
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="form-input"
            placeholder="Password"
            type="password"
            required
            autoComplete="new-password"
          />
        </div>

        <div className="form-group">
          <label htmlFor="roleId" className="input-label">
            Role ID
          </label>
          <input
            id="roleId"
            value={roleId}
            onChange={(e) => setRoleId(e.target.value)}
            className="form-input"
            placeholder="Role ID"
            type="number"
            required
          />
        </div>

        <button type="submit" className="submit-button">
          Register
        </button>
      </form>

      {message && (
        <p
          className={`message ${
            message.includes('success') ? 'success' : 'error'
          }`}
        >
          {message}
        </p>
      )}

      <p className="mt-10 text-center text-sm/6 text-gray-500">
        Already have an account?{' '}
        <a href="/" className="cta-link">
          Sign in
        </a>
      </p>
    </div>
  );
};
