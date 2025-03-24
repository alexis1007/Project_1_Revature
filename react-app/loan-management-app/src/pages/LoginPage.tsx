import React, { useState } from 'react';
import { loginUser } from '../services/authService';
import { useNavigate } from 'react-router-dom';

export const LoginPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await loginUser({ username: username, password });
      if (!response.ok) throw new Error('Login failed');
      navigate('/dashboard');
    } catch (error: unknown) {
      if (error instanceof Error) {
        console.error(error.message);
      } else {
        console.error('Unknown error occurred');
      }
    }
  };

  return (
    <div className="main-container">
      <div className="sm-container">
        <h2 className="form-title">Sign in to your account</h2>
      </div>

      <form className="auth-form" onSubmit={handleLogin}>
        <div className="form-group">
          <label htmlFor="username" className="input-label">
            Username
          </label>
          <input
            id="username"
            value={username}
            placeholder="Username"
            onChange={(e) => setUsername(e.target.value)}
            className="form-input"
          />
        </div>

        <div className="form-group">
          <div className="flex items-center justify-between">
            <label htmlFor="password" className="input-label">
              Password
            </label>
          </div>

          <input
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="form-input"
            placeholder="Password"
            type="password"
            required
            autoComplete="current-password"
          />
          <a href="#" className="forgot-password">
            Forgot password?
          </a>
        </div>

        <button type="submit" className="submit-button">
          Sign in
        </button>
        <p className="mt-10 text-center text-sm/6 text-gray-500">
          Need an account? Register now.{' '}
          <a href="/register" className="cta-link">
            Sign up
          </a>
        </p>
      </form>
    </div>
  );
};
