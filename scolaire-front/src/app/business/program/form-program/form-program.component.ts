import {Component, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';
import {ProgramRequest} from '../../../services/models/program-request';
import {KindResponse} from '../../../services/models/kind-response';
import {ProgramsService} from '../../../services/services/programs.service';
import {ActivatedRoute, Router} from '@angular/router';
import {KindsService} from '../../../services/services/kinds.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-form-program',
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './form-program.component.html',
  styleUrl: './form-program.component.css'
})
export class FormProgramComponent implements OnInit{

  errorMsg: Array<string> = [];
  isEditMode = false;

  programRequest: ProgramRequest = {
    id: undefined,
    name: '',
    description: '',
    kindId: 0,
    archive: false
  }

  kindResponse: KindResponse[] = [];

  constructor(
    private programService: ProgramsService,
    private toastrService: ToastrService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private kindService: KindsService
  ) {
  }

  ngOnInit(): void {
    this.loadKind()
    this.loadProgram()
  }

  loadKind(){
    this.kindService.getAllKinds().subscribe({
      next: (kinds: KindResponse[]) => {
        this.kindResponse = kinds
      },
      error:(err) => {
        console.log('Erreur lors du chargement')
      }
    })
  }

  loadProgram(){
    const programId = this.activatedRoute.snapshot.params['programId'];
    if (programId){
      this.isEditMode = true
      this.programService.getProgramById({
        'id': programId,
      }).subscribe({
        next: (program) => {
          const selectedKind = this.kindResponse.find(kind => kind.name === program.kindName);
          this.programRequest = {
            id: program.id,
            name: program.name as string,
            description: program.description as string,
            kindId: selectedKind?.id ?? 0
          }
        }
      })
    }
  }

  saveProgram(){
    if (this.isEditMode){
      this.updateProgram()
    }else {
      this.addProgram()
    }
  }

  private addProgram(){
    this.programService.addProgram({
      body: this.programRequest
    }).subscribe({
      next: data => {
        this.toastrService.success("Program ajouté")
        this.router.navigate(['/programs'])
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

  private updateProgram(){
    this.programService.updateProgram({
      body: this.programRequest
    }).subscribe({
      next: () =>{
        this.toastrService.success("Program mis a jour");
        this.router.navigate(['/programs']);
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
