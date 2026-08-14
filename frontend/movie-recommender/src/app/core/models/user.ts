// user.model.ts
export interface User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  role: 'ROLE_ADMIN' | 'ROLE_USER' | string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  id: number;
  accessToken: string;
  refreshToken: string;
  email: string;
  firstName: string;
  lastName: string;
  role: string;
}

export interface RegisterRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
}

export interface RegisterResponse {
  message: string;
  activationToken: string;
}