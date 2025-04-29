import {Component, OnInit} from '@angular/core';
import {CourseResponse} from '../../../services/models/course-response';
import {TeacherResponse} from '../../../services/models/teacher-response';
import {CoursesService} from '../../../services/services/courses.service';
import {TeachersService} from '../../../services/services/teachers.service';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {AssignCourseToTeacher$Params} from '../../../services/fn/teachers/assign-course-to-teacher';
import {FormsModule} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-course-teacher',
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './course-teacher.component.html',
  styleUrl: './course-teacher.component.css'
})
export class CourseTeacherComponent implements OnInit{

  errorMsg: Array<string> = [];

  courseResponse: CourseResponse[] = [];
  teacherResponse: TeacherResponse[] = [];

  selectedCourseId: number | undefined
  selectedTeacherId: number | undefined
  constructor(
    private courseService: CoursesService,
    private teacherService: TeachersService,
    private router: Router,
    private toastr: ToastrService
  ) {
  }
  ngOnInit(): void {
    this.loadCourse();
    this.loadTeacher();
  }

  assignCourseToTeacher(){
    if (this.selectedTeacherId !== undefined && this.selectedCourseId !== undefined){
      const params: AssignCourseToTeacher$Params = {
        teacherId: this.selectedTeacherId,
        courseId: this.selectedCourseId
      };
      this.teacherService.assignCourseToTeacher(params).subscribe({
        next: data => {
          this.toastr.success('Course assigné au Teacher avec succès')
          this.router.navigate(['/courses'])
        },
        error: (err) => {
          console.log(err)
        }
      })
    }
  }

  loadCourse(){
    this.courseService.getAllCourses().subscribe({
      next: (course: CourseResponse[]) => {
        this.courseResponse = course
      },
      error:(err) => {
        console.log('Erreur lors du chargement')
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

}
