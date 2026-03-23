import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { NotFound } from './not-found';

describe('NotFound', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NotFound],
      providers: [provideRouter([])],
    }).compileComponents();
  });

  it('should create the component', () => {
    const fixture = TestBed.createComponent(NotFound);
    fixture.detectChanges();
    expect(fixture.componentInstance).toBeTruthy();
  });

  it('should display a 404 heading', () => {
    const fixture = TestBed.createComponent(NotFound);
    fixture.detectChanges();
    const el = fixture.nativeElement as HTMLElement;
    expect(el.querySelector('h1')?.textContent).toContain('404');
  });

  it('should include a link back to the home page', () => {
    const fixture = TestBed.createComponent(NotFound);
    fixture.detectChanges();
    const el = fixture.nativeElement as HTMLElement;
    const homeLink = el.querySelector('a[href="/"]');
    expect(homeLink).not.toBeNull();
  });
});
