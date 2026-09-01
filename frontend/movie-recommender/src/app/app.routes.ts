import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth-guard';
import { RecommendMoviesComponent } from './features/movie-recommendation/movie-recommendation';

export const routes: Routes = [
  { path: '', loadComponent: () => import('./features/home/home').then(m => m.HomeComponent) },
  { path: 'browse', loadComponent: () => import('./features/browse/browse-movies').then(m => m.BrowseMoviesComponent) },
  { path: 'recommendations', canActivate: [authGuard], loadComponent: () => import('./features/movie-recommendation/movie-recommendation').then(m => m.RecommendMoviesComponent) },
  { path: 'movie/:id', loadComponent: () => import('./features/movie-details/movie-details').then(m => m.MovieDetailsComponent) },
  { path: 'login', loadComponent: () => import('./features/auth/login/login').then(m => m.LoginComponent) },
  { path: 'register', loadComponent: () => import('./features/auth/register/register').then(m => m.RegisterComponent) },
  { path: 'profile', canActivate: [authGuard], loadComponent: () => import('./features/profile/profile').then(m => m.ProfileComponent) },//   { path: 'profile/edit', loadComponent: () => import('./features/profile/profile-edit/profile-edit.component').then(m => m.ProfileEditComponent) },
  { path: '**', redirectTo: '' }
];