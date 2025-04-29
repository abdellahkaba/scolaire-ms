import {Component, OnInit} from '@angular/core';
import {TeacherResponse} from '../../../services/models/teacher-response';
import {TeachersService} from '../../../services/services/teachers.service';
import {ToastrService} from 'ngx-toastr';
import {Router, RouterLink} from '@angular/router';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-list-teacher',
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './list-teacher.component.html',
  styleUrl: './list-teacher.component.css'
})
export class ListTeacherComponent implements OnInit{

  response: TeacherResponse[] = []

  constructor(
    private teacherService: TeachersService,
    private toastr: ToastrService,
    private router: Router
  ) {
  }
  ngOnInit(): void {
    this.getAllTeachers()
  }

  private getAllTeachers(){
    this.teacherService.getAllTeachers()
      .subscribe({
        next: (teachers: TeacherResponse[]) => {
          this.response = teachers
        },
        error: (err) => {
          console.log("Erreur lors de chargement")
        }
      })
  }

  editTeacher(teacher: any){
    this.router.navigate(['/edit-teacher', teacher.id])
  }

  deleteTeacher(teacherId: number){
    if (confirm('Êtes-vous sûr de vouloir supprimer cet Sector ?')) {
      this.teacherService.deleteTeacherById({ id: teacherId }).subscribe({
        next: () => {
          this.toastr.success("Teacher supprimé avec succès");
          this.getAllTeachers();
        },
        error: (err) => {
          console.log(err);
          this.toastr.error("Désolé, impossible de supprimer", "Oups !");
        }
      });
    }
  }

}
