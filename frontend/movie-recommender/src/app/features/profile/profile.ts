import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { AuthService } from '../../core/services/auth';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './profile.html',
  styleUrl: './profile.scss'
})
export class ProfileComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);

  currentUser = this.authService.currentUser;

  profileForm = this.fb.nonNullable.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    email: [{ value: '', disabled: true }]
  });

  passwordForm = this.fb.nonNullable.group({
    currentPassword: ['', Validators.required],
    newPassword: ['', Validators.required],
    confirmPassword: ['', Validators.required]
  });

  profileSubmitting = signal(false);
  passwordSubmitting = signal(false);

  profileError = signal<string | null>(null);
  profileSuccess = signal<string | null>(null);

  passwordError = signal<string | null>(null);
  passwordSuccess = signal<string | null>(null);

  constructor() {
    const user = this.currentUser();

    if (user) {
      this.profileForm.patchValue({
        firstName: user.firstName,
        lastName: user.lastName,
        email: user.email
      });
    }
  }

  get initials(): string {
    const user = this.currentUser();

    if (!user) {
      return '';
    }

    return `${user.firstName[0]}${user.lastName[0]}`.toUpperCase();
  }

  saveProfile(): void {
    if (this.profileForm.invalid) {
      this.profileForm.markAllAsTouched();
      return;
    }

    this.profileSubmitting.set(true);
    this.profileError.set(null);
    this.profileSuccess.set(null);

    const data = this.profileForm.getRawValue();

    this.authService.updateProfile({
      firstName: data.firstName,
      lastName: data.lastName
    }).subscribe({
      next: (user) => {
        this.profileSubmitting.set(false);
        this.profileSuccess.set('Profile updated successfully.');

        // Update the current user in AuthService if your
        // AuthService supports doing so.
      },
      error: (err) => {
        this.profileSubmitting.set(false);

        const msg =
          err.error?.message ??
          (typeof err.error === 'string' ? err.error : null);

        this.profileError.set(
          msg ?? 'Failed to update profile. Please try again.'
        );
      }
    });
  }

  changePassword(): void {
    if (this.passwordForm.invalid) {
      this.passwordForm.markAllAsTouched();
      return;
    }

    const {
      currentPassword,
      newPassword,
      confirmPassword
    } = this.passwordForm.getRawValue();

    if (newPassword !== confirmPassword) {
      this.passwordForm.controls.confirmPassword.setErrors({
        passwordMismatch: true
      });

      return;
    }

    this.passwordSubmitting.set(true);
    this.passwordError.set(null);
    this.passwordSuccess.set(null);

    this.authService.changePassword({
      currentPassword,
      newPassword
    }).subscribe({
      next: () => {
        this.passwordSubmitting.set(false);
        this.passwordSuccess.set('Password changed successfully.');

        this.passwordForm.reset();
        this.passwordForm.markAsPristine();
        this.passwordForm.markAsUntouched();
      },
      error: (err) => {
        this.passwordSubmitting.set(false);

        const msg =
          err.error?.message ??
          (typeof err.error === 'string' ? err.error : null);

        this.passwordError.set(
          msg ?? 'Failed to change password. Please try again.'
        );
      }
    });
  }
}