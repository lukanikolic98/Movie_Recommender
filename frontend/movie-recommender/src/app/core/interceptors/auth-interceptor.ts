import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, filter, switchMap, take, throwError } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from '../services/auth';

let isRefreshing = false;
const refreshedToken$ = new BehaviorSubject<string | null>(null);

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  // Don't attach a token to auth endpoints themselves
  if (req.url.includes('/auth/login') || req.url.includes('/auth/register') || req.url.includes('/auth/refresh')) {
    return next(req);
  }

  const token = authService.getAccessToken();
  const authReq = token
    ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
    : req;

  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status !== 401 || !authService.getRefreshToken()) {
        return throwError(() => error);
      }

      if (!isRefreshing) {
        isRefreshing = true;
        refreshedToken$.next(null);

        return authService.refreshAccessToken().pipe(
          switchMap(res => {
            isRefreshing = false;
            refreshedToken$.next(res.accessToken);
            return next(req.clone({ setHeaders: { Authorization: `Bearer ${res.accessToken}` } }));
          }),
          catchError(refreshErr => {
            isRefreshing = false;
            authService.logout();
            return throwError(() => refreshErr);
          })
        );
      }

      // Another request already triggered refresh — wait for it, then retry
      return refreshedToken$.pipe(
        filter(t => t !== null),
        take(1),
        switchMap(newToken =>
          next(req.clone({ setHeaders: { Authorization: `Bearer ${newToken}` } }))
        )
      );
    })
  );
};