import { Component, computed, input } from '@angular/core';
import { Student, StudentType } from '../../../core/models/student.model';

const TYPE_COLORS: Record<StudentType, string> = {
  FISE: '#3b82f6',
  FISA: '#10b981',
  FAT: '#8b5cf6',
};

const STAT_MAX = 100;

@Component({
  selector: 'app-student-card',
  standalone: true,
  templateUrl: './student-card.html',
  styleUrl: './student-card.css',
})
export class StudentCard {
  student = input.required<Student>();

  protected typeColor = computed(() => TYPE_COLORS[this.student().type] ?? '#6b7280');

  protected statPercent(value: number | undefined): number {
    return Math.min(((value ?? 0) / STAT_MAX) * 100, 100);
  }
}
