import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Payment } from '../models/payment.model';
import { Student } from '../models/student.model';

@Injectable({ providedIn: 'root' })
export class AppStateService {
  private studentsSubject = new BehaviorSubject<Student[]>([]);
  private paymentsSubject = new BehaviorSubject<Payment[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);
  private errorSubject = new BehaviorSubject<string | null>(null);

  students$ = this.studentsSubject.asObservable();
  payments$ = this.paymentsSubject.asObservable();
  loading$ = this.loadingSubject.asObservable();
  error$ = this.errorSubject.asObservable();

  setStudents(students: Student[]): void { this.studentsSubject.next(students); }
  setPayments(payments: Payment[]): void { this.paymentsSubject.next(payments); }
  setLoading(loading: boolean): void { this.loadingSubject.next(loading); }
  setError(error: string | null): void { this.errorSubject.next(error); }
}
