import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Student } from '../models/student.model';

@Injectable({ providedIn: 'root' })
export class StudentService {
  private readonly baseUrl = `${environment.apiUrl}/api/students`;

  constructor(private http: HttpClient) {}

  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this.baseUrl);
  }

  getStudentByCode(code: string): Observable<Student> {
    return this.http.get<Student>(`${this.baseUrl}/${code}`);
  }

  getStudentsByProgram(programId: string): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.baseUrl}/program/${programId}`);
  }

  searchStudents(keyword: string): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.baseUrl}/search`, { params: { keyword } });
  }
}
