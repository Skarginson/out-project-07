import { Component, input, output, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Student, StudentType, HumorType } from '../../../core/models/student.model';

@Component({
  selector: 'app-student-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './student-form.html',
  styleUrl: './student-form.css',
})
export class StudentForm implements OnInit {
  student = input<Student | null>(null);
  submitted = output<Partial<Student>>();
  cancelled = output<void>();

  form!: FormGroup;

  readonly studentTypes: StudentType[] = ['FISE', 'FISA', 'FAT'];
  readonly humorTypes: HumorType[] = ['CAFE_ADDICT', 'NOCTAMBULE', 'FANTOME', 'GRIND_MASTER'];

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    const s = this.student();
    this.form = this.fb.group({
      firstName: [s?.firstName ?? '', Validators.required],
      lastName: [s?.lastName ?? '', Validators.required],
      nickname: [s?.nickname ?? ''],
      speciality: [s?.speciality ?? ''],
      type: [s?.type ?? 'FISE', Validators.required],
      humorType: [s?.humorType ?? ''],
      superPower: [s?.superPower ?? ''],
      catchPhrase: [s?.catchPhrase ?? ''],
      imageUrl: [s?.imageUrl ?? ''],
      stats: this.fb.group({
        hp: [s?.stats?.hp ?? 50],
        attack: [s?.stats?.attack ?? 50],
        defense: [s?.stats?.defense ?? 50],
      }),
    });
  }

  onSubmit(): void {
    if (this.form.valid) {
      const raw = this.form.value;
      const student: Partial<Student> = {
        ...raw,
        humorType: raw.humorType || undefined,
        stats: raw.stats
          ? { hp: Number(raw.stats.hp), attack: Number(raw.stats.attack), defense: Number(raw.stats.defense) }
          : undefined,
      };
      this.submitted.emit(student);
    }
  }

  onCancel(): void {
    this.cancelled.emit();
  }
}
