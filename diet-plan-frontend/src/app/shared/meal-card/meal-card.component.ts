import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Meal } from '../../core/models/meal.model';

@Component({
  selector: 'app-meal-card',
  templateUrl: './meal-card.component.html',
  styleUrls: ['./meal-card.component.css']
})
export class MealCardComponent {
  @Input() meal!: Meal;
  @Input() actionLabel = 'Add to Plan';
  @Input() showAction = true;
  @Output() actionClicked = new EventEmitter<Meal>();

  get chipClass() {
    return 'chip chip-' + (this.meal.category?.toLowerCase() ?? '');
  }

  get fallbackImage() {
    return 'https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400';
  }
}
