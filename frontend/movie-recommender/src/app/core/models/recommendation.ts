import { MovieDto } from './movie';

export interface RecommendedMovieDto {
  movie: MovieDto;
  score: number;
}

export interface RecommendationDto {
  movies: RecommendedMovieDto[];
}

export interface RecommendationRequest {
  keywords: string[];
  genres: string[];
  useHistory: boolean;
}

export interface PopularKeywordDto {
  keyword: string;
  likes: number;
}