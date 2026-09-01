import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { environment } from '../../../environments/environment.development';
import { PopularKeywordDto, RecommendationDto, RecommendationRequest } from '../models/recommendation';

@Injectable({ providedIn: 'root' })
export class RecommendationsService {
  private http = inject(HttpClient);

  // POST {apiUrl}/recommendations  body: RecommendationRequest -> RecommendationDto
  private readonly baseUrl = `${environment.apiUrl}/api/movies/recommendations`;

  getRecommendations(request: RecommendationRequest) {
    return this.http.post<RecommendationDto>(this.baseUrl, request);
  }

  getPopularKeywords(limit = 20) {
    const params = new HttpParams().set('limit', limit);
    return this.http.get<PopularKeywordDto[]>(`${this.baseUrl}/keywords/popular`, { params });
  }
}