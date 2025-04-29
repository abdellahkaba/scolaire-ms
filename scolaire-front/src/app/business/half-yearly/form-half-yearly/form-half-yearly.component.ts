import {Component, OnInit} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {ActivatedRoute, Router} from '@angular/router';
import {HalfYearlyService} from '../../../services/services/half-yearly.service';
import {HalfYearlyRequest} from '../../../services/models/half-yearly-request';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-form-half-yearly',
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './form-half-yearly.component.html',
  styleUrl: './form-half-yearly.component.css'
})
export class FormHalfYearlyComponent implements OnInit{

  errorMsg: Array<string> = []
  isEditMode = false

  halfRequest: HalfYearlyRequest = {
    id: undefined,
    name: '',
    description: '',
    archive: false
  }
  constructor(
    private halfService: HalfYearlyService,
    private toastr: ToastrService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
  }
  ngOnInit(): void {
    this.loadHalfyYearly()
  }

  loadHalfyYearly(){
    const halfId = this.activatedRoute.snapshot.params['halfId']
    if (halfId){
      this.isEditMode = true
      this.halfService.getHalfYearlyById({
        'id': halfId
      }).subscribe({
        next: (half) => {
          this.halfRequest = {
            id: half.id,
            name: half.name as string,
            description: half.description as string,
            archive: false
          }
        }
      })
    }
  }

  saveHalfYearly(){
    if (this.isEditMode){
      this.updateHalfYearly();
    }else {
      this.addHalfYearly()
    }
  }

  private addHalfYearly(){
    this.halfService.newHalfYearly({
      body: this.halfRequest
    }).subscribe({
      next: data => {
        this.toastr.success("HalfYearly ajouté");
        this.router.navigate(['/half'])
      },
      error: (err) => {
        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else {
          this.toastr.error('Désolé, une erreur est survenue ou HalfYearly exist', 'Oups!');
        }
      }
    })
  }
  private updateHalfYearly(){
    this.halfService.updateHalfYearly({
      body: this.halfRequest
    }).subscribe({
      next: () =>{
        this.toastr.success("HalfYearly mis a jour");
        this.router.navigate(['/half']);
      },
      error: (err) => {
        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else {
          this.toastr.error('Désolé, une erreur est survenue ou ce HalfYearly existe', 'Oups!');
        }
      }
    })
  }
}
