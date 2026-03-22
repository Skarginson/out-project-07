import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Student } from '../../core/models/student.model';
import { StudentService } from '../../core/services/student.service';
import { StudentForm } from '../../shared/components/student-form/student-form';

@Component({
  selector: 'app-student-edit',
  standalone: true,
  imports: [StudentForm],
  templateUrl: './student-edit.html',
})
export class StudentEdit {
  private studentService = inject(StudentService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  protected student = signal<Student | null>(null);
  protected loading = signal(true);
  protected error = signal<string | null>(null);

  constructor() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.studentService.getById(id).subscribe({
      next: (s) => {
        this.student.set(s);
        this.loading.set(false);
      },
      error: (err: Error) => {
        this.error.set(err.message);
        this.loading.set(false);
      },
    });
  }

  onSubmit(student: Partial<Student>): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.studentService.update(id, student as Student).subscribe({
      next: () => this.router.navigate(['/student', id]),
      error: (err: Error) => console.error(err.message),
    });
  }

  onCancel(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.router.navigate(['/student', id]);
  }
}
