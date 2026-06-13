import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Student } from '../../models/student.model';
import { AppStateService } from '../../services/app-state.service';
import { StudentService } from '../../services/student.service';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html'
})
export class StudentsComponent implements OnInit, AfterViewInit {
  displayedColumns = ['code', 'firstName', 'lastName', 'email', 'programId', 'actions'];
  dataSource = new MatTableDataSource<Student>([]);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private studentService: StudentService, private state: AppStateService, private router: Router) {}

  ngOnInit(): void {
    this.loadStudents();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadStudents(): void {
    this.state.setLoading(true);
    this.studentService.getStudents().subscribe({
      next: students => {
        this.dataSource.data = students;
        this.state.setStudents(students);
        this.state.setLoading(false);
      },
      error: () => this.state.setLoading(false)
    });
  }

  applyFilter(event: Event): void {
    const value = (event.target as HTMLInputElement).value;
    this.dataSource.filter = value.trim().toLowerCase();
  }

  dashboard(student: Student): void {
    this.router.navigate(['/students', student.code, 'dashboard']);
  }
}
