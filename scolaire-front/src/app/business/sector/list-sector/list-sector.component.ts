import {Component, OnInit} from '@angular/core';
import {SectorResponse} from '../../../services/models/sector-response';
import {SectorsService} from '../../../services/services/sectors.service';
import {ToastrService} from 'ngx-toastr';
import {Router, RouterLink} from '@angular/router';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-list-sector',
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './list-sector.component.html',
  styleUrl: './list-sector.component.css'
})
export class ListSectorComponent implements OnInit{

  response: SectorResponse[] = [];
  constructor(
    private sectorService: SectorsService,
    private toastr: ToastrService,
    private router: Router
  ) {
  }
  ngOnInit(): void {
    this.getAllSectors();
  }

  private getAllSectors(){
    this.sectorService.getAllSectors()
      .subscribe({
        next: (sectors: SectorResponse[]) => {
          this.response = sectors
        },
        error: (err) => {
          console.log("Erreur lors de chargement")
        }
      });
  }

  editSector(sector: any){
    this.router.navigate(['/edit-sector', sector.id])
  }

  deleteSector(sectorId: number){
    if (confirm('Êtes-vous sûr de vouloir supprimer cet Sector ?')) {
      this.sectorService.deleteSectorById({ id: sectorId }).subscribe({
        next: () => {
          this.toastr.success("Sector supprimé avec succès");
          this.getAllSectors();
        },
        error: (err) => {
          console.log(err);
          this.toastr.error("Désolé, impossible de supprimer", "Oups !");
        }
      });
    }
  }

}
