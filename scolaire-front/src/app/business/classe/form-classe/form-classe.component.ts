import {Component, OnInit} from '@angular/core';
import {ClasseRequest} from '../../../services/models/classe-request';
import {SectorResponse} from '../../../services/models/sector-response';
import {ToastrService} from 'ngx-toastr';
import {ActivatedRoute, Router} from '@angular/router';
import {ClassesService} from '../../../services/services/classes.service';
import {SectorsService} from '../../../services/services/sectors.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-form-classe',
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './form-classe.component.html',
  styleUrl: './form-classe.component.css'
})
export class FormClasseComponent implements OnInit{

  errorMsg: Array<string> = [];
  isEditMode = false;
  classeRequest: ClasseRequest = {
    id: undefined,
    name: '',
    description: '',
    sectorId: 0,
    archive: false
  }

  sectorResponse: SectorResponse[]=[];

  constructor(
    private toastrService: ToastrService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private classeService: ClassesService,
    private sectorService: SectorsService
  ) {
  }
  ngOnInit(): void {
    this.loadSector();
    this.loadClasse()
  }

  loadSector(){
    this.sectorService.getAllSectors()
      .subscribe({
        next: (sectors: SectorResponse[]) => {
          this.sectorResponse = sectors
        },
        error:(err) => {
          console.log('Erreur lors du chargement')
        }
      })
  }

  loadClasse(){
    const classeId = this.activatedRoute.snapshot.params['classeId'];
    if (classeId){
      this.isEditMode = true
      this.classeService.getClasseById({
        'id': classeId,
      }).subscribe({
        next: (classe) => {
          const selectedSector = this.sectorResponse.find(sector => sector.name === classe.sectorName);
          this.classeRequest = {
            id: classe.id,
            name: classe.name as string,
            description: classe.description as string,
            sectorId: selectedSector?.id ?? 0
          }
        }
      })
    }
  }

  saveClasse(){
    if (this.isEditMode){
      this.updateClasse()
    }else {
      this.addClasse();
    }
  }

  private addClasse(){
    this.classeService.addClasse({
      body: this.classeRequest
    }).subscribe({
      next: data => {
        this.toastrService.success("Classe ajouté")
        this.router.navigate(['/classes'])
      },
      error: (err) => {
        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else {
          this.toastrService.error('Désolé, une erreur est survenue Il se peut que cette Classe est dans ce Sector', 'Oups!');
        }
      }
    })
  }

  private updateClasse(){
    this.classeService.updateClasse({
      body: this.classeRequest
    }).subscribe({
      next: () =>{
        this.toastrService.success("Classe mis a jour");
        this.router.navigate(['/classes']);
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
