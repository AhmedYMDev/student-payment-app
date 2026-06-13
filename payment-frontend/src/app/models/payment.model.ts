export type PaymentType = 'CASH' | 'CHECK' | 'TRANSFER' | 'DEPOSIT';
export type PaymentStatus = 'CREATED' | 'VALIDATED' | 'REJECTED';

export interface Payment {
  id: number;
  date: string;
  amount: number;
  type: PaymentType;
  status: PaymentStatus;
  file: string;
  studentCode: string;
  studentFullName: string;
  studentProgramId: string;
}
