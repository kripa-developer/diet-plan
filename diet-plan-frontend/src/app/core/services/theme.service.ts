import { Injectable, Renderer2, RendererFactory2 } from '@angular/core';

export type AppTheme = 'theme-forest' | 'theme-ocean' | 'theme-sunset' | 'theme-midnight';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private renderer: Renderer2;
  private currentTheme: AppTheme = 'theme-forest';

  constructor(rendererFactory: RendererFactory2) {
    this.renderer = rendererFactory.createRenderer(null, null);
    const savedTheme = localStorage.getItem('app-theme') as AppTheme;
    if (savedTheme) {
      this.setTheme(savedTheme);
    } else {
      this.setTheme('theme-forest');
    }
  }

  setTheme(theme: AppTheme) {
    this.renderer.removeClass(document.body, this.currentTheme);
    this.renderer.addClass(document.body, theme);
    this.currentTheme = theme;
    localStorage.setItem('app-theme', theme);
  }

  getTheme(): AppTheme {
    return this.currentTheme;
  }
}
