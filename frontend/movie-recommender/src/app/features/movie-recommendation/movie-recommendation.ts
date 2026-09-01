import { Component, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

import { MatCardModule } from '@angular/material/card';
import { MatChipInputEvent, MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';

import { RecommendationsService } from '../../core/services/recommendations.service';
import { PopularKeywordDto, RecommendedMovieDto } from '../../core/models/recommendation';
import { tmdbImageUrl } from '../../shared/utils/tmdb-image.util';
import { GENRES } from '../../core/constants/movie-filter.const';

// TODO: replace with a fetch from GET /api/keywords/popular (or similar)
// once that endpoint exists.
const MOCK_SUGGESTED_KEYWORDS = [
  'time travel',
  'heist',
  'superhero',
  'based on a true story',
  'dystopia',
  'coming of age',
  'artificial intelligence',
  'revenge'
];

const PAGE_SIZE = 24;
const PAGE_WINDOW = 2; // page buttons shown on each side of the current page

type PageItem = number | '...';

@Component({
  selector: 'app-recommend-movies',
  standalone: true,
  imports: [
    RouterLink,
    MatCardModule,
    MatChipsModule,
    MatIconModule,
    MatButtonModule,
    MatSlideToggleModule,
    MatProgressSpinnerModule,
    MatTooltipModule,
    ReactiveFormsModule
  ],
  templateUrl: './movie-recommendation.html',
  styleUrl: './movie-recommendation.scss'
})
export class RecommendMoviesComponent {
  private recommendationsService = inject(RecommendationsService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
 
  protected readonly allGenres = GENRES;
  suggestedKeywords = signal<PopularKeywordDto[]>([]);
 
  keywordInput = new FormControl('', { nonNullable: true });
  keywords = signal<string[]>([]);
  selectedGenres = signal<Set<string>>(new Set());
  useHistory = signal(false);
 
  // Backend returns every recommendation in one shot, so pagination here is
  // client-side: keep the full list and slice per page.
  allResults = signal<RecommendedMovieDto[]>([]);
  page = signal(1);
 
  loading = signal(false);
  error = signal<string | null>(null);
  hasSearched = signal(false);
 
  canSubmit = computed(
    () => this.keywords().length > 0 || this.selectedGenres().size > 0 || this.useHistory()
  );
 
  remainingSuggestions = computed(() =>
    this.suggestedKeywords().filter((k) => !this.keywords().includes(k.keyword))
  );
 
  totalPages = computed(() => Math.max(1, Math.ceil(this.allResults().length / PAGE_SIZE)));
 
  pagedResults = computed(() => {
    const start = (this.page() - 1) * PAGE_SIZE;
    return this.allResults().slice(start, start + PAGE_SIZE);
  });
 
  pageItems = computed<PageItem[]>(() => {
    const total = this.totalPages();
    const current = this.page();
 
    if (total <= 1) return [1];
 
    const items: PageItem[] = [1];
    const left = Math.max(2, current - PAGE_WINDOW);
    const right = Math.min(total - 1, current + PAGE_WINDOW);
 
    if (left > 2) items.push('...');
    for (let i = left; i <= right; i++) items.push(i);
    if (right < total - 1) items.push('...');
 
    items.push(total);
    return items;
  });
 
  constructor() {
    this.restoreFromQueryParams();
    this.loadSuggestedKeywords();
  }
 
  private loadSuggestedKeywords(): void {
    this.recommendationsService.getPopularKeywords().subscribe({
      next: (keywords) => this.suggestedKeywords.set(keywords),
      // Suggestions are a nice-to-have, not core functionality — fail quietly
      // and just show no suggestions rather than an error state.
      error: () => this.suggestedKeywords.set([])
    });
  }
 
  private restoreFromQueryParams(): void {
    const params = this.route.snapshot.queryParamMap;
 
    const keywordsParam = params.get('keywords');
    const genresParam = params.get('genres');
 
    this.keywords.set(keywordsParam ? keywordsParam.split(',').filter(Boolean) : []);
    this.selectedGenres.set(
      new Set(genresParam ? genresParam.split(',').filter(Boolean) : [])
    );
    this.useHistory.set(params.get('useHistory') === '1');
    this.page.set(Number(params.get('page')) || 1);
 
    // Re-run the search automatically only if we actually came back with
    // filters set (e.g. via browser back) — a fresh visit starts blank.
    if (this.canSubmit() && params.keys.length > 0) {
      this.getRecommendations({ resetPage: false });
    }
  }
 
  private syncQueryParams(): void {
    this.router.navigate([], {
      relativeTo: this.route,
      replaceUrl: true,
      queryParams: {
        keywords: this.keywords().length > 0 ? this.keywords().join(',') : null,
        genres: this.selectedGenres().size > 0 ? Array.from(this.selectedGenres()).join(',') : null,
        useHistory: this.useHistory() ? '1' : null,
        page: this.page() !== 1 ? this.page() : null
      }
    });
  }
 
  addKeywordFromInput(event: MatChipInputEvent): void {
    const value = (event.value ?? '').trim();
    if (value) {
      this.addKeyword(value);
    }
    event.chipInput?.clear();
    this.keywordInput.setValue('');
  }
 
  addKeyword(value: string): void {
    const normalized = value.trim();
    if (!normalized) return;
 
    this.keywords.update((list) => (list.includes(normalized) ? list : [...list, normalized]));
  }
 
  removeKeyword(value: string): void {
    this.keywords.update((list) => list.filter((k) => k !== value));
  }
 
  toggleGenre(genre: string): void {
    this.selectedGenres.update((set) => {
      const next = new Set(set);
      if (next.has(genre)) {
        next.delete(genre);
      } else {
        next.add(genre);
      }
      return next;
    });
  }
 
  isGenreSelected(genre: string): boolean {
    return this.selectedGenres().has(genre);
  }
 
  posterFor(rec: RecommendedMovieDto): string | null {
    return tmdbImageUrl(rec.movie.posterUrl, 'w342');
  }
 
  goToPage(item: PageItem): void {
    if (item === '...' || item === this.page()) return;
 
    this.page.set(item);
    this.syncQueryParams();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
 
  nextPage(): void {
    if (this.page() < this.totalPages()) this.goToPage(this.page() + 1);
  }
 
  previousPage(): void {
    if (this.page() > 1) this.goToPage(this.page() - 1);
  }
 
  getRecommendations(opts: { resetPage?: boolean } = {}): void {
    if (!this.canSubmit()) return;
 
    if (opts.resetPage ?? true) {
      this.page.set(1);
    }
 
    this.loading.set(true);
    this.error.set(null);
    this.hasSearched.set(true);
    this.syncQueryParams();
 
    this.recommendationsService
      .getRecommendations({
        keywords: this.keywords(),
        genres: Array.from(this.selectedGenres()),
        useHistory: this.useHistory()
      })
      .subscribe({
        next: (result) => {
          this.allResults.set(result.movies);
          this.loading.set(false);
        },
        error: () => {
          this.loading.set(false);
          this.error.set('Failed to get recommendations. Please try again.');
        }
      });
  }
}