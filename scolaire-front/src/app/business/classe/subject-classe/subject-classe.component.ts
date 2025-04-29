import {Component, OnInit} from '@angular/core';
import {ClassesService} from '../../../services/services/classes.service';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {ClasseResponse} from '../../../services/models/classe-response';
import {SubjectResponse} from '../../../services/models/subject-response';
import {SubjectsService} from '../../../services/services/subjects.service';
import {AssignSubjectToClasse$Params} from '../../../services/fn/classes/assign-subject-to-classe';

@Component({
  selector: 'app-subject-classe',
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './subject-classe.component.html',
  styleUrl: './subject-classe.component.css'
})
export class SubjectClasseComponent implements OnInit{

  errorMsg: Array<string> = [];

  classeResponse: ClasseResponse[] = [];
  subjectResponse: SubjectResponse[] = [];

  selectedClasseId: number | undefined
  selectedSubjectId: number | undefined


  constructor(
    private classeService: ClassesService,
    private subjectService: SubjectsService,
    private router: Router,
    private toastr: ToastrService
  ) {
  }

  ngOnInit(): void {
    this.loadSubject();
    this.loadClasse();
  }

  assignSubjectToClasse(){
    if (this.selectedClasseId !== undefined && this.selectedSubjectId !== undefined) {
      const params: AssignSubjectToClasse$Params = {
        classeId: this.selectedClasseId,
        subjectId: this.selectedSubjectId
      };
      this.classeService.assignSubjectToClasse(params)
        .subscribe({
          next: data => {
            this.toastr.success('Subject assigné à la Classe ajouté avec succès')
            this.router.navigate(['/subjects'])
          },
          error: (err) => {
            console.log(err)
          }
        })
    }
  }

  loadSubject(){
    this.subjectService.getAllSubjects()
      .subscribe({
        next: (subjects: SubjectResponse[]) => {
          this.subjectResponse = subjects
        },
        error:(err) => {
          console.log('Erreur lors du chargement')
        }
      })
  }

  loadClasse(){
    this.classeService.getAllClasses()
      .subscribe({
        next: (classes: ClasseResponse[]) => {
          this.classeResponse = classes
        },
        error:(err) => {
          console.log('Erreur lors du chargement')
        }
      })
  }

}
