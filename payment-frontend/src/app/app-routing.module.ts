import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AdminTemplateComponent } from './components/admin-template/admin-template.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { StudentsComponent } from './components/students/students.component';
import { PaymentsComponent } from './components/payments/payments.component';
import { NewPaymentComponent } from './components/new-payment/new-payment.component';
import { PaymentDetailComponent } from './components/payment-detail/payment-detail.component';
import { AuthGuard } from './guards/auth.guard';
import { AuthorizationGuard } from './guards/authorization.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '',
    component: AdminTemplateComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'students', component: StudentsComponent },
      { path: 'students/:code/dashboard', component: DashboardComponent },
      { path: 'payments', component: PaymentsComponent },
      { path: 'payments/new', component: NewPaymentComponent, canActivate: [AuthorizationGuard], data: { roles: ['ADMIN'] } },
      { path: 'payments/:id', component: PaymentDetailComponent }
    ]
  },
  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
