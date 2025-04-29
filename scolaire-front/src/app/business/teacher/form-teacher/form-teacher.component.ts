import {Component, OnInit} from '@angular/core';
import {TeacherRequest} from '../../../services/models/teacher-request';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {TeachersService} from '../../../services/services/teachers.service';
import {FormsModule} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-form-teacher',
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './form-teacher.component.html',
  styleUrl: './form-teacher.component.css'
})
export class FormTeacherComponent implements OnInit{

  errorMsg: Array<string> = []
  isEditMode = false;

  teacherRequest: TeacherRequest = {
    id: undefined,
    firstName: '',
    lastName: '',
    emailPro: '',
    emailPerso: '',
    phoneNumber: '',
    address: '',
    archive: false
  }
  constructor(
    private teacherService: TeachersService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toastrService: ToastrService
  ) {
  }
  ngOnInit(): void {
    this.loadTeacher()
  }

  loadTeacher(){
    const teacherId = this.activatedRoute.snapshot.params['teacherId']
    if (teacherId){
      this.isEditMode = true
      this.teacherService.getTeacherById({
        'id': teacherId
      }).subscribe({
        next: (teacher) => {
          this.teacherRequest = {
            id: teacher.id,
            firstName: teacher.firstName as string,
            lastName: teacher.lastName as string,
            emailPro: teacher.emailPro as string,
            emailPerso: teacher.emailPerso as string,
            phoneNumber: teacher.phoneNumber as string,
            address: teacher.address as string,
            archive: false
          }
        }
      })
    }
  }

  saveTeacher(){
    if (this.isEditMode) {
      this.updateTeacher();
    } else {
      this.addTeacher();
    }
  }

  private addTeacher(){
    this.teacherService.addTeacher({
      body: this.teacherRequest
    }).subscribe({
      next: data => {
        this.toastrService.success("Teacher ajouté");
        this.router.navigate(['/teachers']);
      },
      error: (err) => {
        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else {
          this.toastrService.error('Désolé, une erreur est survenue', 'Oups!');
        }
      }
    })
  }

  private updateTeacher(){
    this.teacherService.updateTeacher({
      body: this.teacherRequest
    }).subscribe({
      next: data => {
        this.toastrService.success("Teacher mis à jour");
        this.router.navigate(['/teachers']);
      },
      error: (err) => {
        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else {
          this.toastrService.error('Désolé, une erreur est survenue', 'Oups!');
        }
      }
    })
  }
}
