import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Payment, PaymentStatus, PaymentType } from '../../models/payment.model';
import { AppStateService } from '../../services/app-state.service';
import { AuthService } from '../../services/auth.service';
import { PaymentService } from '../../services/payment.service';

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html'
})
export class PaymentsComponent implements OnInit, AfterViewInit {
  displayedColumns = ['id', 'date', 'studentFullName', 'studentCode', 'amount', 'type', 'status', 'actions'];
  dataSource = new MatTableDataSource<Payment>([]);
  statuses: PaymentStatus[] = ['CREATED', 'VALIDATED', 'REJECTED'];
  types: PaymentType[] = ['CASH', 'CHECK', 'TRANSFER', 'DEPOSIT'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private paymentService: PaymentService,
    private state: AppStateService,
    public authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadPayments();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadPayments(): void {
    this.state.setLoading(true);
    this.paymentService.getPayments().subscribe({
      next: payments => {
        this.dataSource.data = payments;
        this.state.setPayments(payments);
        this.state.setLoading(false);
      },
      error: () => this.state.setLoading(false)
    });
  }

  filterStatus(status: PaymentStatus | 'ALL'): void {
    if (status === 'ALL') return this.loadPayments();
    this.paymentService.getPaymentsByStatus(status).subscribe(payments => this.dataSource.data = payments);
  }

  filterType(type: PaymentType | 'ALL'): void {
    if (type === 'ALL') return this.loadPayments();
    this.paymentService.getPaymentsByType(type).subscribe(payments => this.dataSource.data = payments);
  }

  applyFilter(event: Event): void {
    const value = (event.target as HTMLInputElement).value;
    this.dataSource.filter = value.trim().toLowerCase();
  }

  openFile(payment: Payment): void {
    window.open(this.paymentService.getPaymentFileUrl(payment.id), '_blank');
  }

  detail(payment: Payment): void {
    this.router.navigate(['/payments', payment.id]);
  }

  updateStatus(payment: Payment, status: PaymentStatus): void {
    this.paymentService.updatePaymentStatus(payment.id, status).subscribe(() => this.loadPayments());
  }
}
