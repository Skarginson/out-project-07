import { Component, inject, signal, computed } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Student, StudentType } from '../../core/models/student.model';
import { StudentService } from '../../core/services/student.service';

const TYPE_COLORS: Record<StudentType, string> = {
  FISE: '#3b82f6',
  FISA: '#10b981',
  FAT: '#8b5cf6',
};

const STAT_MAX = 100;

@Component({
  selector: 'app-student-detail',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './student-detail.html',
  styleUrl: './student-detail.css',
})
export class StudentDetail {
  private studentService = inject(StudentService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  protected student = signal<Student | null>(null);
  protected loading = signal(true);
  protected error = signal<string | null>(null);
  protected notFound = signal(false);

  protected typeColor = computed(() => {
    const s = this.student();
    return s ? (TYPE_COLORS[s.type] ?? '#6b7280') : '#6b7280';
  });

  constructor() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.studentService.getById(id).subscribe({
      next: (s) => {
        this.student.set(s);
        this.loading.set(false);
      },
      error: (err: Error) => {
        this.loading.set(false);
        if (err.message === 'Ressource introuvable') {
          this.notFound.set(true);
        } else {
          this.error.set(err.message);
        }
      },
    });
  }

  protected statPercent(value: number | undefined): number {
    return Math.min(((value ?? 0) / STAT_MAX) * 100, 100);
  }

  protected onDelete(): void {
    const s = this.student();
    if (!s?.id) return;
    const confirmed = window.confirm(
      `Supprimer ${s.firstName} ${s.lastName} ? Cette action est irréversible.`
    );
    if (!confirmed) return;
    this.studentService.delete(s.id).subscribe({
      next: () => this.router.navigate(['/']),
      error: (err: Error) => this.error.set(err.message),
    });
  }
}
