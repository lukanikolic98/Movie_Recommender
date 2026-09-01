import { Component, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { MoviesService } from '../../core/services/movie.service';
import { MovieDto } from '../../core/models/movie';
import { MovieSearchParams, MovieSortOption } from '../../core/models/movie-search';
import { tmdbImageUrl } from '../../shared/utils/tmdb-image.util';
import {
  GENRES,
  LANGUAGE_OPTIONS,
  RATING_OPTIONS,
  SORT_OPTIONS
} from '../../core/constants/movie-filter.const';

const PAGE_SIZE = 24;
const PAGE_WINDOW = 2; // page buttons shown on each side of the current page

type PageItem = number | '...';

@Component({
  selector: 'app-browse-movies',
  standalone: true,
  imports: [
    RouterLink,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './browse-movies.html',
  styleUrl: './browse-movies.scss'
})
export class BrowseMoviesComponent {
  private fb = inject(FormBuilder);
  private moviesService = inject(MoviesService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
 
  protected readonly genres = GENRES;
  protected readonly ratingOptions = RATING_OPTIONS;
  protected readonly languageOptions = LANGUAGE_OPTIONS;
  protected readonly sortOptions = SORT_OPTIONS;
 
  protected readonly years = computed(() => {
    const currentYear = new Date().getFullYear();
    const list: number[] = [];
    for (let y = currentYear + 1; y >= 1950; y--) list.push(y);
    return list;
  });
 
  searchControl = this.fb.nonNullable.control('');
 
  genre = signal<string>('');
  minRating = signal<number>(0);
  year = signal<number>(0);
  language = signal<string>('');
  sortBy = signal<MovieSortOption>('latest');
 
  page = signal(1);
 
  movies = signal<MovieDto[]>([]);
  totalPages = signal(1);
  totalResults = signal(0);
 
  loading = signal(true);
  error = signal<string | null>(null);
 
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
 
    this.searchControl.valueChanges
      .pipe(debounceTime(400), distinctUntilChanged(), takeUntilDestroyed())
      .subscribe(() => {
        this.page.set(1);
        this.runSearch();
      });
 
    this.runSearch();
  }
 
  private restoreFromQueryParams(): void {
    const params = this.route.snapshot.queryParamMap;
    const sortByParam = params.get('sortBy');
 
    this.searchControl.setValue(params.get('term') ?? '', { emitEvent: false });
    this.genre.set(params.get('genre') ?? '');
    this.minRating.set(Number(params.get('minRating')) || 0);
    this.year.set(Number(params.get('year')) || 0);
    this.language.set(params.get('language') ?? '');
    this.sortBy.set(
      this.sortOptions.some((o) => o.value === sortByParam)
        ? (sortByParam as MovieSortOption)
        : 'latest'
    );
    this.page.set(Number(params.get('page')) || 1);
  }
 
  private syncQueryParams(): void {
    this.router.navigate([], {
      relativeTo: this.route,
      replaceUrl: true,
      queryParams: {
        term: this.searchControl.value.trim() || null,
        genre: this.genre() || null,
        minRating: this.minRating() || null,
        year: this.year() || null,
        language: this.language() || null,
        sortBy: this.sortBy() !== 'latest' ? this.sortBy() : null,
        page: this.page() !== 1 ? this.page() : null
      }
    });
  }
 
  onFilterChange(): void {
    this.page.set(1);
    this.runSearch();
  }
 
  goToPage(item: PageItem): void {
    if (item === '...' || item === this.page()) return;
 
    this.page.set(item);
    this.runSearch();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
 
  nextPage(): void {
    if (this.page() < this.totalPages()) this.goToPage(this.page() + 1);
  }
 
  previousPage(): void {
    if (this.page() > 1) this.goToPage(this.page() - 1);
  }
 
  posterFor(movie: MovieDto): string | null {
    return tmdbImageUrl(movie.posterUrl, 'w342');
  }
 
  runSearch(): void {
    this.syncQueryParams();
 
    this.loading.set(true);
    this.error.set(null);
 
    const params: MovieSearchParams = {
      term: this.searchControl.value.trim() || undefined,
      genre: this.genre() || undefined,
      minRating: this.minRating() || undefined,
      year: this.year() || undefined,
      language: this.language() || undefined,
      sortBy: this.sortBy(),
      page: this.page(),
      pageSize: PAGE_SIZE
    };
 
    this.moviesService.browse(params).subscribe({
      next: (result) => {
        this.movies.set(result.movies);
        this.totalPages.set(Math.max(1, result.totalPages));
        this.totalResults.set(result.totalResults);
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Failed to load movies. Please try again.');
      }
    });
  }
}
