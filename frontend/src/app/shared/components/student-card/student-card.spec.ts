import { TestBed } from '@angular/core/testing';
import { StudentCard } from './student-card';
import { Student } from '../../../core/models/student.model';

const mockStudent: Student = {
  id: 1,
  firstName: 'Alice',
  lastName: 'Dupont',
  type: 'FISE',
  stats: { hp: 80, attack: 50, defense: 30 },
};

describe('StudentCard', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentCard],
    }).compileComponents();
  });

  it('should create the component', () => {
    const fixture = TestBed.createComponent(StudentCard);
    fixture.componentRef.setInput('student', mockStudent);
    fixture.detectChanges();
    expect(fixture.componentInstance).toBeTruthy();
  });

  it('should display the student full name', () => {
    const fixture = TestBed.createComponent(StudentCard);
    fixture.componentRef.setInput('student', mockStudent);
    fixture.detectChanges();
    const el = fixture.nativeElement as HTMLElement;
    expect(el.querySelector('.student-name')?.textContent).toContain('Alice Dupont');
  });

  it('should display the type badge', () => {
    const fixture = TestBed.createComponent(StudentCard);
    fixture.componentRef.setInput('student', mockStudent);
    fixture.detectChanges();
    const el = fixture.nativeElement as HTMLElement;
    expect(el.querySelector('.type-badge')?.textContent?.trim()).toBe('FISE');
  });

  it('should show initials placeholder when no imageUrl provided', () => {
    const fixture = TestBed.createComponent(StudentCard);
    fixture.componentRef.setInput('student', mockStudent);
    fixture.detectChanges();
    const el = fixture.nativeElement as HTMLElement;
    const placeholder = el.querySelector('.avatar-placeholder');
    expect(placeholder).not.toBeNull();
    expect(placeholder?.textContent?.trim()).toBe('AD');
  });

  it('should show an img tag when imageUrl is provided', () => {
    const studentWithImage: Student = { ...mockStudent, imageUrl: 'https://example.com/photo.jpg' };
    const fixture = TestBed.createComponent(StudentCard);
    fixture.componentRef.setInput('student', studentWithImage);
    fixture.detectChanges();
    const el = fixture.nativeElement as HTMLElement;
    const img = el.querySelector('img');
    expect(img).not.toBeNull();
    expect(img?.getAttribute('src')).toBe('https://example.com/photo.jpg');
    expect(img?.getAttribute('alt')).toContain('Alice');
  });

  it('should show nickname when provided', () => {
    const studentWithNick: Student = { ...mockStudent, nickname: 'Aly' };
    const fixture = TestBed.createComponent(StudentCard);
    fixture.componentRef.setInput('student', studentWithNick);
    fixture.detectChanges();
    const el = fixture.nativeElement as HTMLElement;
    expect(el.querySelector('.student-nickname')?.textContent).toContain('Aly');
  });

  it('should not render nickname element when nickname is absent', () => {
    const fixture = TestBed.createComponent(StudentCard);
    fixture.componentRef.setInput('student', mockStudent);
    fixture.detectChanges();
    const el = fixture.nativeElement as HTMLElement;
    expect(el.querySelector('.student-nickname')).toBeNull();
  });

  it('should display all three stat values (HP, ATK, DEF)', () => {
    const fixture = TestBed.createComponent(StudentCard);
    fixture.componentRef.setInput('student', mockStudent);
    fixture.detectChanges();
    const values = fixture.nativeElement.querySelectorAll('.stat-value');
    expect(values[0].textContent.trim()).toBe('80');
    expect(values[1].textContent.trim()).toBe('50');
    expect(values[2].textContent.trim()).toBe('30');
  });

  it('should display 0 when stats are missing', () => {
    const studentNoStats: Student = { id: 2, firstName: 'Bob', lastName: 'Martin', type: 'FAT' };
    const fixture = TestBed.createComponent(StudentCard);
    fixture.componentRef.setInput('student', studentNoStats);
    fixture.detectChanges();
    const values = fixture.nativeElement.querySelectorAll('.stat-value');
    expect(values[0].textContent.trim()).toBe('0');
    expect(values[1].textContent.trim()).toBe('0');
    expect(values[2].textContent.trim()).toBe('0');
  });

  it('statPercent should return the correct percentage', () => {
    const fixture = TestBed.createComponent(StudentCard);
    fixture.componentRef.setInput('student', mockStudent);
    fixture.detectChanges();
    const instance = fixture.componentInstance as any;
    expect(instance.statPercent(50)).toBe(50);
    expect(instance.statPercent(100)).toBe(100);
  });

  it('statPercent should cap at 100 for values above max', () => {
    const fixture = TestBed.createComponent(StudentCard);
    fixture.componentRef.setInput('student', mockStudent);
    fixture.detectChanges();
    const instance = fixture.componentInstance as any;
    expect(instance.statPercent(200)).toBe(100);
  });

  it('statPercent should return 0 for undefined values', () => {
    const fixture = TestBed.createComponent(StudentCard);
    fixture.componentRef.setInput('student', mockStudent);
    fixture.detectChanges();
    const instance = fixture.componentInstance as any;
    expect(instance.statPercent(undefined)).toBe(0);
  });
});
