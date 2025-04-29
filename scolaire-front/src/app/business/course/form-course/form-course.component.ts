import {Component, OnInit} from '@angular/core';
import {ClasseResponse} from '../../../services/models/classe-response';
import {SubjectResponse} from '../../../services/models/subject-response';
import {HalfYearlyResponse} from '../../../services/models/half-yearly-response';
import {AcademieYearResponse} from '../../../services/models/academie-year-response';
import {ToastrService} from 'ngx-toastr';
import {ActivatedRoute, Router} from '@angular/router';
import {ClassesService} from '../../../services/services/classes.service';
import {SubjectsService} from '../../../services/services/subjects.service';
import {HalfYearlyService} from '../../../services/services/half-yearly.service';
import {AcademieYearsService} from '../../../services/services/academie-years.service';
import {CoursesService} from '../../../services/services/courses.service';
import {CourseRequest} from '../../../services/models/course-request';
import {FormsModule} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-form-course',
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './form-course.component.html',
  styleUrl: './form-course.component.css'
})
export class FormCourseComponent implements OnInit{

  errorMsg: Array<string> = [];
  isEditMode = false;

  courseRequest: CourseRequest = {
    id: undefined,
    name: '',
    description: '',
    archive: false,
    classeId: 0,
    subjectId: 0,
    halfYearlyId: 0,
    academieYearId: 0
  }

  classeResponse: ClasseResponse[] = [];
  subjectResponse: SubjectResponse[] = [];
  halfResponse: HalfYearlyResponse[] = [];
  academieResponse: AcademieYearResponse[] = [];

  constructor(
    private courseService: CoursesService,
    private classeService: ClassesService,
    private subjectService: SubjectsService,
    private halfService: HalfYearlyService,
    private academieService: AcademieYearsService,
    private toastrService: ToastrService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    this.loadClasse();
    this.loadSubject();
    this.loadHalfYearly();
    this.loadAcademieYear();
    this.loadCourse();
    this.loadCourse();
  }


  loadCourse(){
    const courseId = this.activatedRoute.snapshot.params['courseId'];
    if (courseId){
      this.isEditMode = true
      this.courseService.getCourseById({
        'id': courseId
      }).subscribe({
        next: (course) => {
          const selectedClasse = this.classeResponse.find(classe => classe.name === course.classeName);
          const selectedSubject = this.subjectResponse.find(subject => subject.name === course.subjectName);
          const selectedHalfYearly = this.halfResponse.find(half => half.name === course.halfYearlyName);
          const selectedAcademie = this.academieResponse.find(academie => academie.name === course.academieName);

          this.courseRequest = {
            id: course.id,
            name: course.name as string,
            description: course.description as string,
            classeId: selectedClasse?.id ?? 0,
            subjectId: selectedSubject?.id ?? 0,
            halfYearlyId: selectedHalfYearly?.id ?? 0,
            academieYearId: selectedAcademie?.id ?? 0

          }
        }
      })
    }
  }

  saveCourse(){
    if (this.isEditMode){
      this.updateCourse()
    }else {
      this.addCourse()
    }
  }

  private addCourse(){
    this.courseService.addCourse({
      body: this.courseRequest
    }).subscribe({
      next: data => {
        this.toastrService.success("Course ajouté");
        this.router.navigate(['/courses'])
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

  private updateCourse(){
    this.courseService.updateCourse({
      body: this.courseRequest
    }).subscribe({
      next: () => {
        this.toastrService.success("Course mis a jour");
        this.router.navigate(['/courses'])
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

  loadClasse(){
    this.classeService.getAllClasses().subscribe({
      next: (classes: ClasseResponse[]) => {
        this.classeResponse = classes
      },
      error:(err) => {
        console.log('Erreur lors du chargement')
      }
    })
  }

  loadSubject(){
    this.subjectService.getAllSubjects().subscribe({
      next: (subjects: SubjectResponse[]) => {
        this.subjectResponse = subjects
      },
      error:(err) => {
        console.log('Erreur lors du chargement')
      }
    })
  }

  loadHalfYearly(){
    this.halfService.getAllHalfYearly().subscribe({
      next: (halfs: HalfYearlyResponse[]) => {
        this.halfResponse = halfs
      },
      error:(err) => {
        console.log('Erreur lors du chargement')
      }
    })
  }

  loadAcademieYear(){
    this.academieService.getAllAcademieYears().subscribe({
      next: (academies: AcademieYearResponse[]) => {
        this.academieResponse = academies
      },
      error:(err) => {
        console.log('Erreur lors du chargement')
      }
    })
  }
}
