import {Component, OnInit} from '@angular/core';
import {ClasseResponse} from '../../../services/models/classe-response';
import {ToastrService} from 'ngx-toastr';
import {Router, RouterLink} from '@angular/router';
import {ClassesService} from '../../../services/services/classes.service';
import {NgIf} from '@angular/common';
import {SubjectResponse} from '../../../services/models/subject-response';
import {SubjectsService} from '../../../services/services/subjects.service';
import {AssignSubjectToClasse$Params} from '../../../services/fn/classes/assign-subject-to-classe';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-list-classe',
  imports: [
    NgIf,
    RouterLink,
    FormsModule,
  ],
  templateUrl: './list-classe.component.html',
  styleUrl: './list-classe.component.css'
})
export class ListClasseComponent implements OnInit{
  response: ClasseResponse[] = [];
  subjectResponse: SubjectResponse[] = []
  selectedClasseId: number | undefined
  selectedSubjectId: number | undefined
  constructor(
    private classeService: ClassesService,
    private toastr: ToastrService,
    private router: Router,
    private subjectService: SubjectsService
  ) {
  }
  ngOnInit(): void {
    this.getAllClasses();
    this.loadSubject()
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
            this.toastr.success('Subject ajouté avec succès')
            this.router.navigate(['/classes'])
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

  private getAllClasses(){
    this.classeService.getAllClasses()
      .subscribe({
        next: (classes: ClasseResponse[]) => {
          this.response = classes
        },
        error: (err) => {
          console.log("Erreur lors de chargement")
        }
      })
  }

  editClasse(classe: any){
    this.router.navigate(['/edit-classe', classe.id])
  }

  deleteClasse(classeId: number){
    if (confirm('Êtes-vous sûr de vouloir supprimer cette Classe ?')) {
      this.classeService.deleteClasseById({ id: classeId }).subscribe({
        next: () => {
          this.toastr.success("Classe supprimé avec succès");
          this.getAllClasses();
        },
        error: (err) => {
          console.log(err);
          this.toastr.error("Désolé, impossible de supprimer", "Oups !");
        }
      });
    }
  }
}
