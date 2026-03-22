import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Student } from '../../core/models/student.model';
import { StudentService } from '../../core/services/student.service';
import { StudentForm } from '../../shared/components/student-form/student-form';

@Component({
  selector: 'app-student-new',
  standalone: true,
  imports: [StudentForm],
  templateUrl: './student-new.html',
})
export class StudentNew {
  private studentService = inject(StudentService);
  private router = inject(Router);

  onSubmit(student: Partial<Student>): void {
    this.studentService.create(student as Student).subscribe({
      next: (created) => this.router.navigate(['/student', created.id]),
      error: (err: Error) => console.error(err.message),
    });
  }

  onCancel(): void {
    this.router.navigate(['/']);
  }
}
