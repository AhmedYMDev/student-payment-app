import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Payment, PaymentStatus, PaymentType } from '../models/payment.model';

@Injectable({ providedIn: 'root' })
export class PaymentService {
  private readonly baseUrl = `${environment.apiUrl}/api/payments`;

  constructor(private http: HttpClient) {}

  getPayments(): Observable<Payment[]> {
    return this.http.get<Payment[]>(this.baseUrl);
  }

  getPayment(id: number): Observable<Payment> {
    return this.http.get<Payment>(`${this.baseUrl}/${id}`);
  }

  getPaymentsByStudentCode(code: string): Observable<Payment[]> {
    return this.http.get<Payment[]>(`${this.baseUrl}/student/${code}`);
  }

  getPaymentsByStatus(status: PaymentStatus): Observable<Payment[]> {
    return this.http.get<Payment[]>(`${this.baseUrl}/status/${status}`);
  }

  getPaymentsByType(type: PaymentType): Observable<Payment[]> {
    return this.http.get<Payment[]>(`${this.baseUrl}/type/${type}`);
  }

  createPayment(formData: FormData): Observable<Payment> {
    return this.http.post<Payment>(this.baseUrl, formData);
  }

  updatePaymentStatus(id: number, status: PaymentStatus): Observable<Payment> {
    return this.http.patch<Payment>(`${this.baseUrl}/${id}/status`, { status });
  }

  getPaymentFileUrl(id: number): string {
    return `${this.baseUrl}/${id}/file`;
  }
}
