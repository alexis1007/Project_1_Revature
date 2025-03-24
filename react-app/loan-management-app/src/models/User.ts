import { Role } from './Role';

export interface User {
  username: string;
  password: string;
  role?: Role;
}
