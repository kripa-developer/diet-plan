import { Component, OnInit } from '@angular/core';
import { DietPlanService } from '../../core/services/diet-plan.service';
import { DietPlan } from '../../core/models/diet-plan.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-diet-plans',
  templateUrl: './diet-plans.component.html',
  styleUrls: ['./diet-plans.component.css']
})
export class DietPlansComponent implements OnInit {
  plans: DietPlan[] = [];
  loading = true;
  showCreate = false;
  newPlan = { name: '', description: '', goal: '', startDate: '' };
  creating = false;
  goals = ['Weight Loss', 'Muscle Gain', 'Maintenance', 'Healthy Eating', 'Sports Performance'];

  constructor(
    private planService: DietPlanService,
    private snack: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit() { this.load(); }

  load() {
    this.loading = true;
    this.planService.getMyPlans().subscribe(p => { this.plans = p; this.loading = false; });
  }

  create() {
    if (!this.newPlan.name) return;
    this.creating = true;
    this.planService.createPlan(this.newPlan).subscribe({
      next: p => {
        this.plans.unshift(p);
        this.showCreate = false;
        this.newPlan = { name: '', description: '', goal: '', startDate: '' };
        this.creating = false;
        this.snack.open('Plan created!', 'Close', { duration: 2000 });
      },
      error: () => { this.creating = false; }
    });
  }

  deletePlan(id: number, e: Event) {
    e.stopPropagation();
    if (!confirm('Delete this plan?')) return;
    this.planService.deletePlan(id).subscribe(() => {
      this.plans = this.plans.filter(p => p.id !== id);
      this.snack.open('Plan deleted', 'Close', { duration: 2000 });
    });
  }

  viewPlan(id: number) { this.router.navigate(['/plans', id]); }
}
