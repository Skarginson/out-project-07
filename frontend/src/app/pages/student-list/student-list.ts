import { Component } from '@angular/core';
import { Gallery } from '../../features/gallery/gallery';

@Component({
  selector: 'app-student-list',
  imports: [Gallery],
  templateUrl: './student-list.html',
})
export class StudentList {}
