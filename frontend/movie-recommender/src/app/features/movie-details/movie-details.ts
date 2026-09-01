import { Component, computed, inject, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location, DatePipe } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Observable } from 'rxjs';

import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTooltipModule } from '@angular/material/tooltip';

import { AuthService } from '../../core/services/auth';
import { MoviesService } from '../../core/services/movie.service';
import { ReviewsService } from '../../core/services/review.service';
import { MovieDto } from '../../core/models/movie';
import { ReviewDto } from '../../core/models/review';
import { tmdbImageUrl } from '../../shared/utils/tmdb-image.util';
import { initialsFromName } from '../../shared/utils/initials.util';

type MovieActionKey = 'like' | 'dislike' | 'watchlist' | 'watched' | null;

@Component({
  selector: 'app-movie-details',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    DatePipe,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatDividerModule,
    MatListModule,
    MatFormFieldModule,
    MatInputModule,
    MatTooltipModule
  ],
  templateUrl: './movie-details.html',
  styleUrl: './movie-details.scss'
})
export class MovieDetailsComponent {
  private route = inject(ActivatedRoute);
  private location = inject(Location);
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private moviesService = inject(MoviesService);
  private reviewsService = inject(ReviewsService);

  protected readonly ratingOptions = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

  actorsExpanded = signal(false);

  visibleActors = computed(() => {
    const movie = this.movie();
    if (!movie) return [];
    return this.actorsExpanded() ? movie.actors : movie.actors.slice(0, 3);
  });

  hasMoreActors = computed(() => (this.movie()?.actors.length ?? 0) > 3);

  private movieId = signal<number | null>(null);

  // ── Movie
  movie = signal<MovieDto | null>(null);
  loading = signal(true);
  loadError = signal<string | null>(null);

  pendingAction = signal<MovieActionKey>(null);
  actionError = signal<string | null>(null);

  // ── Reviews
  reviews = signal<ReviewDto[]>([]);
  reviewsLoading = signal(true);
  reviewsError = signal<string | null>(null);

  reviewSubmitting = signal(false);
  reviewSubmitError = signal<string | null>(null);

  deletingReviewId = signal<number | null>(null);

  reviewForm = this.fb.nonNullable.group({
    comment: ['', [Validators.required, Validators.minLength(3)]],
    rating: [8, [Validators.required, Validators.min(1), Validators.max(10)]]
  });

  isAuthenticated = this.authService.isAuthenticated;

  posterSrc = computed(() => tmdbImageUrl(this.movie()?.posterUrl, 'w500'));

  year = computed(() => {
    const date = this.movie()?.releaseDate;
    return date ? date.slice(0, 4) : null;
  });

  runtimeLabel = computed(() => {
    const minutes = this.movie()?.runtime;

    if (minutes === null || minutes === undefined) {
      return null;
    }

    const h = Math.floor(minutes / 60);
    const m = minutes % 60;

    return h > 0 ? `${h}h ${m}m` : `${m}m`;
  });

  constructor() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.movieId.set(id);
    this.loadMovie();
    this.loadReviews();
  }

  goBack(): void {
    this.location.back();
  }

  initials(name: string): string {
    return initialsFromName(name);
  }

  isOwnReview(review: ReviewDto): boolean {
    const user = this.authService.currentUser();
    return !!user && user.id === review.userId;
  }

  // ── Movie loading & actions

  loadMovie(): void {
    const id = this.movieId();
    if (id === null) return;

    this.loading.set(true);
    this.loadError.set(null);

    this.moviesService.getMovieById(id).subscribe({
      next: (movie) => {
        this.movie.set(movie);
        this.loading.set(false);
      },
      error: (err) => {
        this.loading.set(false);
        this.loadError.set(
          err.status === 404 ? 'Movie not found.' : 'Failed to load movie. Please try again.'
        );
      }
    });
  }

  react(liked: boolean): void {
    const movie = this.movie();
    if (!movie) return;

    const key: MovieActionKey = liked ? 'like' : 'dislike';
    const request = liked
      ? this.moviesService.like(movie.id)
      : this.moviesService.dislike(movie.id);

    this.runMovieAction(key, request);
  }

  toggleWatchlist(): void {
    const movie = this.movie();
    if (!movie) return;

    this.runMovieAction('watchlist', this.moviesService.toggleWatchlist(movie.id));
  }

  toggleWatched(): void {
    const movie = this.movie();
    if (!movie) return;

    this.runMovieAction('watched', this.moviesService.toggleWatched(movie.id));
  }

  private runMovieAction(key: MovieActionKey, request: Observable<MovieDto>): void {
    this.actionError.set(null);
    this.pendingAction.set(key);

    request.subscribe({
      next: (updated) => {
        this.movie.set(updated);
        this.pendingAction.set(null);
      },
      error: (err) => {
        this.pendingAction.set(null);

        const msg =
          err.error?.message ?? (typeof err.error === 'string' ? err.error : null);

        this.actionError.set(
          err.status === 401
            ? 'Log in to react to movies.'
            : (msg ?? 'Something went wrong. Please try again.')
        );
      }
    });
  }

  // ── Reviews

  loadReviews(): void {
    const id = this.movieId();
    if (id === null) return;

    this.reviewsLoading.set(true);
    this.reviewsError.set(null);

    this.reviewsService.getReviews(id).subscribe({
      next: (reviews) => {
        this.reviews.set(reviews);
        this.reviewsLoading.set(false);
      },
      error: () => {
        this.reviewsLoading.set(false);
        this.reviewsError.set('Failed to load reviews.');
      }
    });
  }

  toggleActors(): void {
    this.actorsExpanded.update((v) => !v);
  }

  submitReview(): void {
    const id = this.movieId();
    if (id === null) return;

    if (this.reviewForm.invalid) {
      this.reviewForm.markAllAsTouched();
      return;
    }

    this.reviewSubmitting.set(true);
    this.reviewSubmitError.set(null);

    const { comment, rating } = this.reviewForm.getRawValue();

    this.reviewsService.addReview(id, { comment, rating }).subscribe({
      next: () => {
        this.reviewSubmitting.set(false);
        this.reviewForm.reset({ comment: '', rating: 8 });
        this.loadReviews();
        this.loadMovie(); // keep reviewAverage/reviewCount in sync
      },
      error: (err) => {
        this.reviewSubmitting.set(false);
        this.reviewSubmitError.set(
          err.status === 401
            ? 'Log in to leave a review.'
            : 'Failed to submit review. Please try again.'
        );
      }
    });
  }

  setRating(value: number): void {
    this.reviewForm.controls.rating.setValue(value);
  }

  deleteReview(review: ReviewDto): void {
    const id = this.movieId();
    if (id === null) return;

    this.deletingReviewId.set(review.id);

    this.reviewsService.deleteReview(id, review.id).subscribe({
      next: () => {
        this.deletingReviewId.set(null);
        this.reviews.update((list) => list.filter((r) => r.id !== review.id));
        this.loadMovie();
      },
      error: () => {
        this.deletingReviewId.set(null);
        this.reviewsError.set('Failed to delete review. Please try again.');
      }
    });
  }
}