import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../core/services/user.service';
import { User } from '../../core/models/user.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  form: FormGroup;
  user: User | null = null;
  loading = true;
  saving = false;

  genders = ['MALE', 'FEMALE', 'OTHER'];
  activityLevels = [
    { value: 'SEDENTARY',         label: 'Sedentary (little/no exercise)' },
    { value: 'LIGHTLY_ACTIVE',    label: 'Lightly Active (1–3 days/week)' },
    { value: 'MODERATELY_ACTIVE', label: 'Moderately Active (3–5 days/week)' },
    { value: 'VERY_ACTIVE',       label: 'Very Active (6–7 days/week)' },
    { value: 'EXTRA_ACTIVE',      label: 'Extra Active (physical job)' },
  ];

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private snack: MatSnackBar
  ) {
    this.form = this.fb.group({
      name:          ['', Validators.required],
      age:           [null, [Validators.min(1), Validators.max(120)]],
      weightKg:      [null, [Validators.min(1)]],
      heightCm:      [null, [Validators.min(50)]],
      gender:        [''],
      activityLevel: [''],
    });
  }

  ngOnInit() {
    this.userService.getProfile().subscribe(u => {
      this.user = u;
      this.form.patchValue(u);
      this.loading = false;
    });
  }

  save() {
    if (this.form.invalid) return;
    this.saving = true;
    this.userService.updateProfile(this.form.value).subscribe({
      next: u => {
        this.user = u;
        this.saving = false;
        this.snack.open('Profile updated!', 'Close', { duration: 2500 });
      },
      error: () => { this.saving = false; }
    });
  }

  get bmiStatus(): string {
    const b = this.user?.bmi ?? 0;
    if (!b) return '';
    if (b < 18.5) return 'Underweight';
    if (b < 25)   return 'Normal';
    if (b < 30)   return 'Overweight';
    return 'Obese';
  }
}
