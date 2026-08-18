export type TmdbImageSize = 'w92' | 'w154' | 'w185' | 'w342' | 'w500' | 'w780' | 'original';

const TMDB_IMAGE_BASE = 'https://image.tmdb.org/t/p';

/**
 * Builds a full TMDB image URL from the path stored in the DB
 * (e.g. "/jGWpG4YhpQwVmjyHEGkxEkeRf0S.jpg").
 */
export function tmdbImageUrl(
  path: string | null | undefined,
  size: TmdbImageSize = 'w500'
): string | null {
  if (!path) {
    return null;
  }

  return `${TMDB_IMAGE_BASE}/${size}${path}`;
}