import { Component, inject, signal } from '@angular/core';
import { Student } from '../../core/models/student.model';
import { StudentService } from '../../core/services/student.service';
import { StudentCard } from '../../shared/components/student-card/student-card';

@Component({
  selector: 'app-gallery',
  standalone: true,
  imports: [StudentCard],
  templateUrl: './gallery.html',
  styleUrl: './gallery.css',
})
export class Gallery {
  private studentService = inject(StudentService);

  protected students = signal<Student[]>([]);
  protected loading = signal(true);
  protected error = signal<string | null>(null);
  protected readonly skeletons = Array(8).fill(0);

  constructor() {
    this.studentService.getAll().subscribe({
      next: (data) => {
        this.students.set(data);
        this.loading.set(false);
      },
      error: (err: Error) => {
        this.error.set(err.message);
        this.loading.set(false);
      },
    });
  }
}
