import { Component } from '@angular/core';
import { AiService, AiMealResponse } from '../../core/services/ai.service';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-ai-meal-generator',
  templateUrl: './ai-meal-generator.component.html',
  styleUrls: ['./ai-meal-generator.component.css']
})
export class AiMealGeneratorComponent {
  prompt = '';
  loading = false;
  suggestion: AiMealResponse | null = null;

  constructor(
    private aiService: AiService,
    private dialogRef: MatDialogRef<AiMealGeneratorComponent>,
    private snack: MatSnackBar
  ) {}

  generate() {
    this.loading = true;
    this.suggestion = null;
    this.aiService.getSuggestion(this.prompt).subscribe({
      next: (res: AiMealResponse) => {
        this.suggestion = res;
        this.loading = false;
      },
      error: () => {
        this.snack.open('Failed to connect to AI engine', 'Close', { duration: 3000 });
        this.loading = false;
      }
    });
  }

  close() {
    this.dialogRef.close();
  }
}
