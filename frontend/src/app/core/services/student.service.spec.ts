import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { StudentService } from './student.service';
import { Student } from '../models/student.model';

const mockStudent: Student = {
  id: 1,
  firstName: 'Alice',
  lastName: 'Dupont',
  type: 'FISE',
  stats: { hp: 100, attack: 50, defense: 30 }
};

const mockStudents: Student[] = [
  mockStudent,
  { id: 2, firstName: 'Bob', lastName: 'Martin', type: 'FAT' }
];

describe('StudentService', () => {
  let service: StudentService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        StudentService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(StudentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getAll()', () => {
    it('should return all students via GET /api/students', () => {
      service.getAll().subscribe(students => {
        expect(students.length).toBe(2);
        expect(students).toEqual(mockStudents);
      });

      const req = httpMock.expectOne('http://localhost:8080/api/students');
      expect(req.request.method).toBe('GET');
      req.flush(mockStudents);
    });
  });

  describe('getById()', () => {
    it('should return a student via GET /api/students/:id', () => {
      service.getById(1).subscribe(student => {
        expect(student).toEqual(mockStudent);
      });

      const req = httpMock.expectOne('http://localhost:8080/api/students/1');
      expect(req.request.method).toBe('GET');
      req.flush(mockStudent);
    });

    it('should propagate an error message on 404', () => {
      service.getById(99).subscribe({
        error: (err: Error) => {
          expect(err.message).toBe('Ressource introuvable');
        }
      });

      const req = httpMock.expectOne('http://localhost:8080/api/students/99');
      req.flush('Not Found', { status: 404, statusText: 'Not Found' });
    });
  });

  describe('create()', () => {
    it('should create a student via POST /api/students', () => {
      const newStudent: Student = { firstName: 'Clara', lastName: 'Leroy', type: 'FISA' };

      service.create(newStudent).subscribe(student => {
        expect(student).toEqual({ ...newStudent, id: 3 });
      });

      const req = httpMock.expectOne('http://localhost:8080/api/students');
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(newStudent);
      req.flush({ ...newStudent, id: 3 });
    });
  });

  describe('update()', () => {
    it('should update a student via PUT /api/students/:id', () => {
      const updated: Student = { ...mockStudent, nickname: 'Ali' };

      service.update(1, updated).subscribe(student => {
        expect(student.nickname).toBe('Ali');
      });

      const req = httpMock.expectOne('http://localhost:8080/api/students/1');
      expect(req.request.method).toBe('PUT');
      expect(req.request.body).toEqual(updated);
      req.flush(updated);
    });
  });

  describe('delete()', () => {
    it('should delete a student via DELETE /api/students/:id', () => {
      service.delete(1).subscribe(res => {
        expect(res).toBeNull();
      });

      const req = httpMock.expectOne('http://localhost:8080/api/students/1');
      expect(req.request.method).toBe('DELETE');
      req.flush(null);
    });
  });

  describe('handleError()', () => {
    it('should return a generic message for unexpected errors', () => {
      service.getAll().subscribe({
        error: (err: Error) => {
          expect(err.message).toBe('Une erreur inattendue est survenue');
        }
      });

      const req = httpMock.expectOne('http://localhost:8080/api/students');
      req.flush('Server Error', { status: 500, statusText: 'Internal Server Error' });
    });

    it('should return a network error message when server is unreachable', () => {
      service.getAll().subscribe({
        error: (err: Error) => {
          expect(err.message).toBe('Impossible de contacter le serveur');
        }
      });

      const req = httpMock.expectOne('http://localhost:8080/api/students');
      req.error(new ProgressEvent('error'));
    });
  });
});
