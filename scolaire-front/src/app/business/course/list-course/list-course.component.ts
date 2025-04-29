import {Component, OnInit} from '@angular/core';
import {CourseResponse} from '../../../services/models/course-response';
import {ToastrService} from 'ngx-toastr';
import {Router, RouterLink} from '@angular/router';
import {CoursesService} from '../../../services/services/courses.service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-list-course',
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './list-course.component.html',
  styleUrl: './list-course.component.css'
})
export class ListCourseComponent implements OnInit{

  response: CourseResponse[] = [];

  constructor(
    private courseService: CoursesService,
    private toastr: ToastrService,
    private router: Router
  ) {
  }
  ngOnInit(): void {
    this.getAllCourse();
  }

  private getAllCourse(){
    this.courseService.getAllCourses()
      .subscribe({
        next: (courses: CourseResponse[]) => {
          this.response = courses
        },
        error: (err) => {
          console.log("Erreur lors de chargement")
        }
      })
  }

  editCourse(course: any){
    this.router.navigate(['/edit-course', course.id])
  }

  deleteCourse(courseId: number){
    if (confirm('Êtes-vous sûr de vouloir supprimer cet Course ?')) {
      this.courseService.deleteCourseById({ id: courseId }).subscribe({
        next: () => {
          this.toastr.success("Course supprimé avec succès");
          this.getAllCourse();
        },
        error: (err) => {
          console.log(err);
          this.toastr.error("Désolé, impossible de supprimer", "Oups !");
        }
      });
    }
  }

}
