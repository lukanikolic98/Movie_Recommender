import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from '../../../environments/environment.development';
import { ReviewDto } from '../models/review';

@Injectable({ providedIn: 'root' })
export class ReviewsService {
  private http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/api/movies`;

  getReviews(movieId: number) {
    return this.http.get<ReviewDto[]>(`${this.baseUrl}/${movieId}/reviews`);
  }

  addReview(movieId: number, review: Pick<ReviewDto, 'comment' | 'rating'>) {
    return this.http.post<ReviewDto>(`${this.baseUrl}/${movieId}/reviews`, review);
  }

  deleteReview(movieId: number, reviewId: number) {
    return this.http.delete(`${this.baseUrl}/${movieId}/reviews/${reviewId}`, {
      responseType: 'text'
    });
  }
}