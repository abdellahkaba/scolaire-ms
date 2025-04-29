import {Component, OnInit} from '@angular/core';
import {KindRequest} from '../../../services/models/kind-request';
import {KindsService} from '../../../services/services/kinds.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {FormsModule} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-form-kind',
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './form-kind.component.html',
  styleUrl: './form-kind.component.css'
})
export class FormKindComponent implements OnInit{
  errorMsg: Array<string> = []
  isEditMode = false

  kindRequest: KindRequest = {
    id: undefined,
    name: '',
    description: '',
    archive: false
  }
  constructor(
    private kindService: KindsService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toastrService: ToastrService
  ) {
  }
  ngOnInit(): void {
    const kindId = this.activatedRoute.snapshot.params['kindId'];
    if (kindId){
      this.isEditMode = true
      this.kindService.getKindById({
        'id': kindId
      }).subscribe({
        next: (kind) => {
          this.kindRequest = {
            id: kind.id,
            name: kind.name as string,
            description: kind.description as string,
            archive: kind.archive
          }
        }
      })
    }
  }

  saveKind() {
    if (this.isEditMode){
      this.updateKind();
    }else {
      this.addKind()
    }
  }

  private addKind() {
    this.kindService.addKind1({
      body: this.kindRequest
    }).subscribe({
      next: data => {
        this.toastrService.success("Kind ajouté")
        this.router.navigate(['/kinds'])
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

  private updateKind(){
    this.kindService.updateKind({
      body: this.kindRequest
    }).subscribe({
      next: () =>{
        this.toastrService.success("Kind mis a jour");
        this.router.navigate(['/kinds']);
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
