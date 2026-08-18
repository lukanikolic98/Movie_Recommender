import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap, catchError, of } from 'rxjs';
import { ChangePasswordRequest, LoginRequest, LoginResponse, RegisterRequest, RegisterResponse, UpdateProfileRequest, User } from '../models/user';
import { environment } from '../../../environments/environment.development';

const ACCESS_TOKEN_KEY = 'access_token';
const REFRESH_TOKEN_KEY = 'refresh_token';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private currentUserSignal = signal<User | null>(null);
  private readonly baseUrl = `${environment.apiUrl}/auth`;
  
  currentUser = this.currentUserSignal.asReadonly();
  isAuthenticated = computed(() => this.currentUserSignal() !== null);
  
  constructor(private http: HttpClient, private router: Router) {}
  
  login(credentials: LoginRequest) {
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, credentials).pipe(
      tap(res => this.setSession(res))
    );
  }
  
  register(data: RegisterRequest) {
    return this.http.post<RegisterResponse>(`${this.baseUrl}/register`, data);
  }
  
  fetchCurrentUser() {
    if (!this.getAccessToken()) {
      this.currentUserSignal.set(null);
      return of(null);
    }
    return this.http.get<User>(`${this.baseUrl}/me`).pipe(
      tap(user => this.currentUserSignal.set(user)),
      catchError(() => {
        this.currentUserSignal.set(null);
        return of(null);
      })
    );
  }
  
  logout(): void {
    localStorage.removeItem(ACCESS_TOKEN_KEY);
    localStorage.removeItem(REFRESH_TOKEN_KEY);
    this.currentUserSignal.set(null);
    this.router.navigate(['/']);
  }
  
  refreshAccessToken() {
    const refreshToken = localStorage.getItem(REFRESH_TOKEN_KEY);
    return this.http.post<{ accessToken: string }>(`${this.baseUrl}/refresh`, { refreshToken }).pipe(
      tap(res => localStorage.setItem(ACCESS_TOKEN_KEY, res.accessToken))
    );
  }
  
  getRefreshToken(): string | null {
    return localStorage.getItem(REFRESH_TOKEN_KEY);
  }
  
  getAccessToken(): string | null {
    return localStorage.getItem(ACCESS_TOKEN_KEY);
  }
  
  private setSession(res: LoginResponse): void {
    localStorage.setItem(ACCESS_TOKEN_KEY, res.accessToken);
    localStorage.setItem(REFRESH_TOKEN_KEY, res.refreshToken);
    const user: User = {
      id: res.id,
      email: res.email,
      firstName: res.firstName,
      lastName: res.lastName,
      role: res.role
    };
    this.currentUserSignal.set(user);
  }
  updateProfile(data: UpdateProfileRequest) {
    return this.http.put<User>(`${this.baseUrl}/profile`, data).pipe(
      tap(user => this.currentUserSignal.set(user))
    );
  }

  changePassword(data: ChangePasswordRequest) {
    return this.http.put<void>(`${this.baseUrl}/change-password`, data);
  }
}