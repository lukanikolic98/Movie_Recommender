export interface MovieDto {
  id: number;
  title: string;
  overview: string | null;
  director: string | null;
  posterUrl: string | null;
  language: string | null;
  releaseDate: string | null; // ISO date string, e.g. "2010-07-16"
  runtime: number | null;

  // user-specific status (null if not logged in or no interaction yet)
  liked: boolean | null;
  watchlisted: boolean | null;
  watched: boolean | null;

  genres: string[];
  keywords: string[];
  actors: string[];

  reviewAverage: number | null;
  tmdbVoteAverage: number | null;
  tmdbVoteCount: number | null;
  reviewCount: number;
}