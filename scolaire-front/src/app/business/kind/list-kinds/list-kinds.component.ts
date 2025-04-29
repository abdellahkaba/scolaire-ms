import {Component, OnInit} from '@angular/core';
import {KindResponse} from '../../../services/models/kind-response';
import {KindsService} from '../../../services/services/kinds.service';
import {ToastrService} from 'ngx-toastr';
import {Router, RouterLink} from '@angular/router';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-list-kinds',
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './list-kinds.component.html',
  styleUrl: './list-kinds.component.css'
})
export class ListKindsComponent implements OnInit{

  response: KindResponse[] = [];
  constructor(
    private kindService: KindsService,
    private toastr: ToastrService,
    private router: Router
  ) {
  }
  ngOnInit(): void {
    this.getAllKinds()
  }

  private getAllKinds(){
    this.kindService.getAllKinds()
      .subscribe({
        next: (kinds: KindResponse[]) => {
          this.response = kinds
        },
        error: (err) => {
          console.log("Erreur lors de chargement")
        }
      });
  }
  editKind(kind: any){
    this.router.navigate(['/edit-kind', kind.id])
  }

  deleteKind(kindId: number){
    if (confirm('Êtes-vous sûr de vouloir supprimer cet Kind ?')) {
      this.kindService.deleteKindById({ id: kindId }).subscribe({
        next: () => {
          this.toastr.success("Kind supprimé avec succès");
          this.getAllKinds();
        },
        error: (err) => {
          console.log(err);
          this.toastr.error("Désolé, impossible de supprimer", "Oups !");
        }
      });
    }
  }
}
