import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Payment } from '../../models/payment.model';
import { Student } from '../../models/student.model';
import { PaymentService } from '../../services/payment.service';
import { StudentService } from '../../services/student.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  student: Student | null = null;
  students: Student[] = [];
  payments: Payment[] = [];
  totalAmount = 0;
  validatedCount = 0;
  rejectedCount = 0;
  createdCount = 0;

  constructor(
    private route: ActivatedRoute,
    private studentService: StudentService,
    private paymentService: PaymentService
  ) {}

  ngOnInit(): void {
    const code = this.route.snapshot.paramMap.get('code');
    if (code) {
      this.loadStudentDashboard(code);
    } else {
      this.loadGlobalDashboard();
    }
  }

  loadGlobalDashboard(): void {
    this.studentService.getStudents().subscribe(students => this.students = students);
    this.paymentService.getPayments().subscribe(payments => this.setPayments(payments));
  }

  loadStudentDashboard(code: string): void {
    this.studentService.getStudentByCode(code).subscribe(student => this.student = student);
    this.paymentService.getPaymentsByStudentCode(code).subscribe(payments => this.setPayments(payments));
  }

  setPayments(payments: Payment[]): void {
    this.payments = payments;
    this.totalAmount = payments.reduce((sum, p) => sum + p.amount, 0);
    this.validatedCount = payments.filter(p => p.status === 'VALIDATED').length;
    this.rejectedCount = payments.filter(p => p.status === 'REJECTED').length;
    this.createdCount = payments.filter(p => p.status === 'CREATED').length;
  }
}
