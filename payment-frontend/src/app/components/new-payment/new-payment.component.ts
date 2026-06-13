import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PaymentType } from '../../models/payment.model';
import { PaymentService } from '../../services/payment.service';

@Component({
  selector: 'app-new-payment',
  templateUrl: './new-payment.component.html'
})
export class NewPaymentComponent {
  types: PaymentType[] = ['CASH', 'CHECK', 'TRANSFER', 'DEPOSIT'];
  selectedFile: File | null = null;
  error = '';

  form = this.fb.group({
    studentCode: ['', Validators.required],
    amount: [0, [Validators.required, Validators.min(1)]],
    type: ['CASH' as PaymentType, Validators.required],
    date: [new Date(), Validators.required]
  });

  constructor(private fb: FormBuilder, private paymentService: PaymentService, private router: Router) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.selectedFile = input.files && input.files.length > 0 ? input.files[0] : null;
  }

  submit(): void {
    if (this.form.invalid || !this.selectedFile) {
      this.error = 'Tous les champs et le PDF sont obligatoires';
      return;
    }

    const formData = new FormData();
    formData.append('studentCode', this.form.value.studentCode || '');
    formData.append('amount', String(this.form.value.amount || 0));
    formData.append('type', this.form.value.type || 'CASH');
    formData.append('date', this.formatDate(this.form.value.date as Date));
    formData.append('file', this.selectedFile);

    this.paymentService.createPayment(formData).subscribe({
      next: () => this.router.navigateByUrl('/payments'),
      error: err => this.error = err.error?.message || 'Erreur pendant la sauvegarde du paiement'
    });
  }

  private formatDate(date: Date): string {
    const d = new Date(date);
    const month = `${d.getMonth() + 1}`.padStart(2, '0');
    const day = `${d.getDate()}`.padStart(2, '0');
    return `${d.getFullYear()}-${month}-${day}`;
  }
}
