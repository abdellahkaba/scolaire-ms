import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {SubjectRequest} from '../../../services/models/subject-request';
import {SubjectsService} from '../../../services/services/subjects.service';
import {FormsModule} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-form-subject',
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './form-subject.component.html',
  styleUrl: './form-subject.component.css'
})
export class FormSubjectComponent implements OnInit{

  errorMsg: Array<string> = []
  isEditMode = false

  subjectRequest: SubjectRequest = {
    id: undefined,
    name: '',
    description: '',
    archive: false
  }

  constructor(
    private subjectService: SubjectsService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService
  ) {
  }

  ngOnInit() {
    this.loadSubject()
  }

  loadSubject(){
    const subjectId = this.activatedRoute.snapshot.params['subjectId']
    if (subjectId){
      this.isEditMode = true
      this.subjectService.getSubjectById({
        'id': subjectId
      }).subscribe({
        next: (subject) => {
          this.subjectRequest = {
            id: subject.id,
            name: subject.name as string,
            description: subject.description as string,
            archive: subject.archive
          }
        }
      })
    }
  }

  saveSubject() {
    if (this.isEditMode){
      this.updateSubject();
    }else {
      this.addSubject()
    }
  }

  private addSubject(){
    this.subjectService.addSubject({
      body: this.subjectRequest
    }).subscribe({
      next: data => {
        this.toastr.success("Subject ajouté");
        this.router.navigate(['/subjects'])
      },
      error: (err) => {
        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else {
          this.toastr.error('Désolé, une erreur est survenue ou Subject existe', 'Oups!');
        }
      }
    })
  }

  private updateSubject(){
    this.subjectService.updateSubject({
      body: this.subjectRequest
    }).subscribe({
      next: () =>{
        this.toastr.success("Subject mis a jour");
        this.router.navigate(['/subjects']);
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
