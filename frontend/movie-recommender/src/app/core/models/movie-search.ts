import { MovieDto } from './movie';

export type MovieSortOption =
  | 'latest'
  | 'oldest'
  | 'rating_desc'
  | 'rating_asc'
  | 'popularity_asc'
  | 'popularity_desc';

export interface MovieSearchParams {
  term?: string;
  genre?: string; // omit/empty = All
  minRating?: number; // omit/0 = All
  year?: number; // omit/0 = All
  language?: string; // omit/empty = All
  sortBy?: MovieSortOption;
  page: number; // 1-based
  pageSize: number;
}

export interface MovieSearchResult {
  movies: MovieDto[];
  page: number;
  totalPages: number;
  totalResults: number;
}