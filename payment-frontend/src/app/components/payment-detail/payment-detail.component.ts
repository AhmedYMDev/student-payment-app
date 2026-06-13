import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Payment, PaymentStatus } from '../../models/payment.model';
import { AuthService } from '../../services/auth.service';
import { PaymentService } from '../../services/payment.service';

@Component({
  selector: 'app-payment-detail',
  templateUrl: './payment-detail.component.html'
})
export class PaymentDetailComponent implements OnInit {
  payment: Payment | null = null;
  statuses: PaymentStatus[] = ['CREATED', 'VALIDATED', 'REJECTED'];

  constructor(
    private route: ActivatedRoute,
    private paymentService: PaymentService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.paymentService.getPayment(id).subscribe(payment => this.payment = payment);
  }

  openFile(): void {
    if (!this.payment) return;
    window.open(this.paymentService.getPaymentFileUrl(this.payment.id), '_blank');
  }

  updateStatus(status: PaymentStatus): void {
    if (!this.payment) return;
    this.paymentService.updatePaymentStatus(this.payment.id, status).subscribe(payment => this.payment = payment);
  }
}
