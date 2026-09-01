export const GENRES: string[] = [
  'Action',
  'Adventure',
  'Animation',
  'Comedy',
  'Crime',
  'Documentary',
  'Drama',
  'Family',
  'Fantasy',
  'History',
  'Horror',
  'Music',
  'Mystery',
  'Romance',
  'Science Fiction',
  'Thriller',
  'TV Movie',
  'War',
  'Western'
];

// ISO 639-1 codes actually present in the DB.
export const LANGUAGE_CODES: string[] = [
  'af', 'ar', 'cn', 'cs', 'da', 'de', 'el', 'en', 'es', 'fa',
  'fr', 'he', 'hi', 'hu', 'id', 'is', 'it', 'ja', 'ko', 'ky',
  'nb', 'nl', 'no', 'pl', 'ps', 'pt', 'ro', 'ru', 'sl', 'sv',
  'ta', 'te', 'th', 'tr', 'vi', 'xx', 'zh'
];

export interface LanguageOption {
  code: string;
  label: string;
}

// Human-readable labels via Intl, falling back to the raw code
// (covers 'cn' and 'xx', which aren't real Intl.DisplayNames entries).
function languageLabel(code: string): string {
  try {
    const displayNames = new Intl.DisplayNames(['en'], { type: 'language' });
    const name = displayNames.of(code);
    return name && name !== code ? name : code.toUpperCase();
  } catch {
    return code.toUpperCase();
  }
}

export const LANGUAGE_OPTIONS: LanguageOption[] = LANGUAGE_CODES
  .map((code) => ({ code, label: languageLabel(code) }))
  .sort((a, b) => a.label.localeCompare(b.label));

export const RATING_OPTIONS: number[] = [9, 8, 7, 6, 5, 4, 3, 2, 1];

export interface SortOptionDef {
  value:
    | 'latest'
    | 'oldest'
    | 'rating_desc'
    | 'rating_asc'
    | 'popularity_desc'
    | 'popularity_asc';
  label: string;
}

export const SORT_OPTIONS: SortOptionDef[] = [
  { value: 'latest', label: 'Latest' },
  { value: 'oldest', label: 'Oldest' },
  { value: 'rating_desc', label: 'Rating (Descending)' },
  { value: 'rating_asc', label: 'Rating (Ascending)' },
  { value: 'popularity_desc', label: 'Popularity (Descending)' },
  { value: 'popularity_asc', label: 'Popularity (Ascending)' }
];