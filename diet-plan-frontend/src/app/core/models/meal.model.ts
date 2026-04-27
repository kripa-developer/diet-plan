export interface Meal {
  id?: number;
  name: string;
  description?: string;
  category: 'BREAKFAST' | 'LUNCH' | 'DINNER' | 'SNACK' | 'DRINK';
  calories: number;
  proteinG?: number;
  carbsG?: number;
  fatG?: number;
  fiberG?: number;
  imageUrl?: string;
}
