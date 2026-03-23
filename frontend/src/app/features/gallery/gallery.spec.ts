import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { of, throwError, Subject } from 'rxjs';
import { Gallery } from './gallery';
import { StudentService } from '../../core/services/student.service';
import { Student } from '../../core/models/student.model';

const mockStudents: Student[] = [
  { id: 1, firstName: 'Alice', lastName: 'Dupont', type: 'FISE', stats: { hp: 80, attack: 50, defense: 30 } },
  { id: 2, firstName: 'Bob', lastName: 'Martin', type: 'FAT' },
];

function createGallery(serviceOverride: Partial<StudentService>) {
  TestBed.configureTestingModule({
    imports: [Gallery],
    providers: [
      { provide: StudentService, useValue: serviceOverride },
      provideRouter([]),
    ],
  });
  const fixture = TestBed.createComponent(Gallery);
  fixture.detectChanges();
  return fixture;
}

describe('Gallery', () => {
  it('should render the page title', async () => {
    const fixture = createGallery({ getAll: () => of(mockStudents) });
    await fixture.whenStable();
    fixture.detectChanges();
    const el = fixture.nativeElement as HTMLElement;
    expect(el.querySelector('h1')?.textContent).toContain('PROMODEX');
  });

  it('should show skeleton cards while loading', () => {
    const pending$ = new Subject<Student[]>();
    const fixture = createGallery({ getAll: () => pending$ });
    fixture.detectChanges();
    const el = fixture.nativeElement as HTMLElement;
    expect(el.querySelector('.skeleton-card')).not.toBeNull();
  });

  it('should display student cards once data is loaded', async () => {
    const fixture = createGallery({ getAll: () => of(mockStudents) });
    await fixture.whenStable();
    fixture.detectChanges();
    const el = fixture.nativeElement as HTMLElement;
    expect(el.querySelectorAll('app-student-card').length).toBe(2);
  });

  it('should show error message when service fails', async () => {
    const fixture = createGallery({
      getAll: () => throwError(() => new Error('Impossible de contacter le serveur')),
    });
    await fixture.whenStable();
    fixture.detectChanges();
    const el = fixture.nativeElement as HTMLElement;
    expect(el.querySelector('.error-state')?.textContent).toContain('Impossible de contacter le serveur');
  });

  it('should show empty state when no students are returned', async () => {
    const fixture = createGallery({ getAll: () => of([]) });
    await fixture.whenStable();
    fixture.detectChanges();
    const el = fixture.nativeElement as HTMLElement;
    expect(el.querySelector('.empty-state')?.textContent).toContain('Aucun étudiant trouvé');
  });

  it('should render links pointing to each student detail page', async () => {
    const fixture = createGallery({ getAll: () => of(mockStudents) });
    await fixture.whenStable();
    fixture.detectChanges();
    const links = fixture.nativeElement.querySelectorAll('a.card-link');
    expect(links.length).toBe(2);
    expect(links[0].getAttribute('href')).toContain('/student/1');
    expect(links[1].getAttribute('href')).toContain('/student/2');
  });
});
