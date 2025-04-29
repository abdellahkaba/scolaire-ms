import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {SectorRequest} from '../../../services/models/sector-request';
import {SectorsService} from '../../../services/services/sectors.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-form-sector',
    imports: [
        FormsModule,
        NgForOf,
        NgIf
    ],
  templateUrl: './form-sector.component.html',
  styleUrl: './form-sector.component.css'
})
export class FormSectorComponent implements OnInit{
  errorMsg: Array<string> = []
  isEditMode = false

  sectorRequest: SectorRequest = {
    id: undefined,
    name: '',
    description: '',
    archive: false
  }

  constructor(
    private sectorService: SectorsService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService
  ) {
  }

  ngOnInit() {
    this.loadSector()
  }

  loadSector(){
    const sectorId = this.activatedRoute.snapshot.params['sectorId']
    if (sectorId){
      this.isEditMode = true
      this.sectorService.getSectorById({
        'id': sectorId
      }).subscribe({
        next: (sector) => {
          this.sectorRequest = {
            id: sector.id,
            name: sector.name as string,
            description: sector.description as string,
            archive: sector.archive
          }
        }
      })
    }
  }

  saveSector() {
    if (this.isEditMode){
      this.updateSector();
    }else {
      this.addSector()
    }
  }

  private addSector(){
    this.sectorService.addKind({
      body: this.sectorRequest
    }).subscribe({
      next: data => {
        this.toastr.success("Sector ajouté");
        this.router.navigate(['/sectors'])
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

  private updateSector(){
    this.sectorService.updateSector({
      body: this.sectorRequest
    }).subscribe({
      next: () =>{
        this.toastr.success("Sector mis a jour");
        this.router.navigate(['/sectors']);
      },
      error: (err) => {
        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else {
          this.toastr.error('Désolé, une erreur est survenue ou ce Sector existe', 'Oups!');
        }
      }
    })
  }

}
