import {Component, OnInit} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {Router, RouterLink} from '@angular/router';
import {SubjectResponse} from '../../../services/models/subject-response';
import {SubjectsService} from '../../../services/services/subjects.service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-list-subject',
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './list-subject.component.html',
  styleUrl: './list-subject.component.css'
})
export class ListSubjectComponent implements OnInit{


  response: SubjectResponse[] = [];
  constructor(
    private subjectService: SubjectsService,
    private toastr: ToastrService,
    private router: Router
  ) {
  }
  ngOnInit(): void {
    this.getAllSubjects();
  }

  private getAllSubjects(){
    this.subjectService.getAllSubjects()
      .subscribe({
        next: (subjects: SubjectResponse[]) => {
          this.response = subjects
        },
        error: (err) => {
          console.log("Erreur lors de chargement")
        }
      });
  }

  editSubject(subject: any){
    this.router.navigate(['/edit-subject', subject.id])
  }

  deleteSubject(subjectId: number){
    if (confirm('Êtes-vous sûr de vouloir supprimer cet Subject ?')) {
      this.subjectService.deleteSubjectById({ id: subjectId }).subscribe({
        next: () => {
          this.toastr.success("Subject supprimé avec succès");
          this.getAllSubjects();
        },
        error: (err) => {
          console.log(err);
          this.toastr.error("Désolé, impossible de supprimer", "Oups !");
        }
      });
    }
  }
}
