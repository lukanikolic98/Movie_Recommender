import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { environment } from '../../../environments/environment.development';
import { MovieDto } from '../models/movie';
import { MovieSearchParams, MovieSearchResult } from '../models/movie-search';

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

  /**
   * NOTE: targets GET /api/movies/browse, which does not exist on the
   * backend yet — the current /api/movies/search only accepts `term` and
   * returns a flat, unpaginated list. This is the contract the backend
   * needs to implement: term + optional genre/minRating/year/language/sortBy
   * filters, plus page/pageSize, returning a paged result.
   */
  browse(params: MovieSearchParams) {
    let httpParams = new HttpParams()
      .set('page', params.page)
      .set('pageSize', params.pageSize);

    if (params.term) httpParams = httpParams.set('term', params.term);
    if (params.genre) httpParams = httpParams.set('genre', params.genre);
    if (params.minRating) httpParams = httpParams.set('minRating', params.minRating);
    if (params.year) httpParams = httpParams.set('year', params.year);
    if (params.language) httpParams = httpParams.set('language', params.language);
    if (params.sortBy) httpParams = httpParams.set('sortBy', params.sortBy);

    return this.http.get<MovieSearchResult>(`${this.baseUrl}/browse`, { params: httpParams });
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