import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { environment } from '../../../environments/environment.development';
import { MovieDto } from '../models/movie';

@Injectable({ providedIn: 'root' })
export class MoviesService {
  private http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/api/movies`;

  getAllMovies() {
    return this.http.get<MovieDto[]>(this.baseUrl);
  }

  getMovieById(id: number) {
    return this.http.get<MovieDto>(`${this.baseUrl}/${id}`);
  }

  search(term: string) {
    const params = new HttpParams().set('term', term);
    return this.http.get<MovieDto[]>(`${this.baseUrl}/search`, { params });
  }

  like(id: number) {
    return this.http.post<MovieDto>(`${this.baseUrl}/${id}/like`, {});
  }

  dislike(id: number) {
    return this.http.post<MovieDto>(`${this.baseUrl}/${id}/dislike`, {});
  }

  toggleWatchlist(id: number) {
    return this.http.post<MovieDto>(`${this.baseUrl}/${id}/watchlist`, {});
  }

  toggleWatched(id: number) {
    return this.http.post<MovieDto>(`${this.baseUrl}/${id}/watched`, {});
  }

  deleteMovie(id: number) {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }
}