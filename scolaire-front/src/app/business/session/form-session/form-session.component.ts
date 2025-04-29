import {Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';
import {SessionRequest} from '../../../services/models/session-request';
import {TeacherResponse} from '../../../services/models/teacher-response';
import {CourseResponse} from '../../../services/models/course-response';
import {CoursesService} from '../../../services/services/courses.service';
import {TeachersService} from '../../../services/services/teachers.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {SessionsService} from '../../../services/services/sessions.service';

@Component({
  selector: 'app-form-session',
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './form-session.component.html',
  styleUrl: './form-session.component.css'
})
export class FormSessionComponent implements OnInit{

  errorMsg: Array<string> = [];
  isEditMode = false;

  sessionRequest: SessionRequest = {
    id: undefined,
    name: '',
    bigInDate: '',
    endDate: '',
    description: '',
    teacherId: 0,
    courseId: 0,
    archive: false
  }

  teacherResponse: TeacherResponse[] = [];
  courseResponse: CourseResponse[] = [];

  constructor(
    private courseService: CoursesService,
    private teacherService: TeachersService,
    private router: Router,
    private toastr: ToastrService,
    private sessionService: SessionsService,
    private activatedRoute: ActivatedRoute
  ) {
  }
  ngOnInit(): void {
    this.loadSession();
    this.loadCourse();
    this.loadTeacher();
  }

  loadSession(){
    const sessionId = this.activatedRoute.snapshot.params['sessionId']
    if (sessionId){
      this.isEditMode = true
      this.sessionService.getSessionById({
        'id': sessionId
      }).subscribe({
        next: (session) => {
          const selectedTeacher = this.teacherResponse.find(teacher => teacher.firstName === session.teacherFirstName)
          const selectedCourse = this.courseResponse.find(course => course.name === session.courseName)
          this.sessionRequest = {
            id: session.id,
            name: session.name as string,
            bigInDate: session.bigInDate as string,
            endDate: session.endDate as string,
            description: session.description as string,
            teacherId: selectedTeacher?.id ?? 0,
            courseId: selectedCourse?.id ?? 0

          }
        }
      })
    }
  }

  saveSession(){
    if (this.isEditMode){
      this.updateSession()
    }else {
      this.addSession()
    }
  }
  private addSession(){
    this.sessionService.addSession({
      body: this.sessionRequest
    }).subscribe({
      next: data => {
        this.toastr.success("Session ajoutée")
        this.router.navigate(['/sessions'])
      },
      error: (err) => {
        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else {
          this.toastr.error('Désolé, une erreur est survenue', 'Oups!');
        }
      }
    })
  }

  loadTeacher(){
    this.teacherService.getAllTeachers().subscribe({
      next: (teachers: TeacherResponse[]) => {
        this.teacherResponse = teachers
      },
      error:(err) => {
        console.log('Erreur lors du chargement')
      }
    })
  }

  loadCourse(){
    this.courseService.getAllCourses().subscribe({
      next: (courses: CourseResponse[]) => {
        this.courseResponse = courses
      },
      error:(err) => {
        console.log('Erreur lors du chargement')
      }
    })
  }

  private updateSession(){
    this.sessionService.updateSession({
      body: this.sessionRequest
    }).subscribe({
      next: () =>{
        this.toastr.success("Session mis a jour");
        this.router.navigate(['/sessions']);
      },
      error: (err) => {
        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else {
          this.toastr.error('Désolé, une erreur est survenue', 'Oups!');
        }
      }
    })
  }

}
