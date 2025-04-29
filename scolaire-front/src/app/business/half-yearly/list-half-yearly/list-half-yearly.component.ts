import {Component, OnInit} from '@angular/core';
import {HalfYearlyResponse} from '../../../services/models/half-yearly-response';
import {ToastrService} from 'ngx-toastr';
import {Router, RouterLink} from '@angular/router';
import {HalfYearlyService} from '../../../services/services/half-yearly.service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-list-half-yearly',
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './list-half-yearly.component.html',
  styleUrl: './list-half-yearly.component.css'
})
export class ListHalfYearlyComponent implements OnInit{

  response: HalfYearlyResponse[] = [];
  constructor(
    private halfService: HalfYearlyService,
    private toastr: ToastrService,
    private router: Router
  ) {
  }
  ngOnInit(): void {
    this.getHalfYearly()
  }

  private getHalfYearly(){
    this.halfService.getAllHalfYearly()
      .subscribe({
        next: (half: HalfYearlyResponse[]) => {
          this.response = half
        },
        error: (err) => {
          console.log("Erreur lors de chargement")
        }
      });
  }

  editHalfYearly(half: any){
    this.router.navigate(['/edit-half', half.id])
  }

  deleteHalfYearly(halfId: number){
    if (confirm('Êtes-vous sûr de vouloir supprimer cet Semestre ?')) {
      this.halfService.deleteHalfYearlyById({ id: halfId }).subscribe({
        next: () => {
          this.toastr.success("HalfYearly supprimé avec succès");
          this.getHalfYearly();
        },
        error: (err) => {
          console.log(err);
          this.toastr.error("Désolé, impossible de supprimer", "Oups !");
        }
      });
    }
  }



}
