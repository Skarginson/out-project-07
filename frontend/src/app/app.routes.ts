import { Routes } from '@angular/router';
import { StudentList } from './pages/student-list/student-list';
import { StudentDetail } from './pages/student-detail/student-detail';
import { StudentNew } from './pages/student-new/student-new';
import { StudentEdit } from './pages/student-edit/student-edit';
import { NotFound } from './pages/not-found/not-found';

export const routes: Routes = [
  { path: '', component: StudentList },
  { path: 'student/new', component: StudentNew },
  { path: 'student/:id', component: StudentDetail },
  { path: 'student/:id/edit', component: StudentEdit },
  { path: '**', component: NotFound },
];
