import { Component, OnInit, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { ThemeService, AppTheme } from '../../core/services/theme.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  isLoggedIn = false;
  isAdmin = false;
  userName = '';
  menuOpen = false;
  scrolled = false;

  themes: { name: string, value: AppTheme, color: string, icon: string }[] = [
    { name: 'Forest (Light)',  value: 'theme-forest',   color: '#1a6b45', icon: 'park' },
    { name: 'Ocean Breeze',    value: 'theme-ocean',    color: '#0ea5e9', icon: 'waves' },
    { name: 'Sunset Glow',     value: 'theme-sunset',   color: '#f97316', icon: 'wb_sunny' },
    { name: 'Midnight (Dark)', value: 'theme-midnight', color: '#1e293b', icon: 'dark_mode' },
  ];

  constructor(
    private auth: AuthService, 
    private router: Router,
    public themeService: ThemeService
  ) {}

  ngOnInit() {
    this.auth.isLoggedIn$.subscribe(v => {
      this.isLoggedIn = v;
      const u = this.auth.getCurrentUser();
      this.userName = u?.name ?? '';
      this.isAdmin = this.auth.isAdmin();
    });
  }

  @HostListener('window:scroll')
  onScroll() { this.scrolled = window.scrollY > 20; }

  changeTheme(t: AppTheme) {
    this.themeService.setTheme(t);
  }

  get currentThemeIcon(): string {
    return this.themes.find(t => t.value === this.themeService.getTheme())?.icon || 'palette';
  }

  logout() { this.auth.logout(); this.menuOpen = false; }
  toggleMenu() { this.menuOpen = !this.menuOpen; }
  closeMenu() { this.menuOpen = false; }
}
