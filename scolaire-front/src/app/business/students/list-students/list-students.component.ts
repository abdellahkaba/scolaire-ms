import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {StudentResponse} from '../../../services/models/student-response';
import {StudentsService} from '../../../services/services/students.service';
import {ToastrService} from 'ngx-toastr';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-list-students',
  imports: [
    RouterLink,
    NgIf
  ],
  templateUrl: './list-students.component.html',
  styleUrl: './list-students.component.css'
})
export class ListStudentsComponent implements OnInit{

  response: StudentResponse[] = [];
  constructor(
    private studentService: StudentsService,
    private toastr: ToastrService,
    private router: Router
  ) {
  }
  ngOnInit(): void {
    this.getAllStudents();
  }

  private getAllStudents(){
    this.studentService.getAllStudents()
      .subscribe({
        next: (students: StudentResponse[]) => {
          this.response = students
        },
        error: (err) => {
          console.error("Erreur lors du chargement")
        }
      })
  }
  editStudent(student: any) {
    this.router.navigate(['/edit-student', student.id]);
  }
  deleteStudent(studentId: number) {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet étudiant ?')) {
      this.studentService.deleteStudentById({ id: studentId }).subscribe({
        next: () => {
          this.toastr.success("Étudiant supprimé avec succès");
          this.getAllStudents();
        },
        error: (err) => {
          console.log(err);
          this.toastr.error("Désolé, impossible de supprimer", "Oups !");
        }
      });
    }
  }
}
