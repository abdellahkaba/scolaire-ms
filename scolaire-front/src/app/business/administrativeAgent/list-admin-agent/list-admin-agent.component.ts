import {Component, OnInit} from '@angular/core';
import {AdministrativeAgentResponse} from '../../../services/models/administrative-agent-response';
import {AdministrativeAgentService} from '../../../services/services/administrative-agent.service';
import {ToastrService} from 'ngx-toastr';
import {Router, RouterLink} from '@angular/router';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-list-admin-agent',
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './list-admin-agent.component.html',
  styleUrl: './list-admin-agent.component.css'
})
export class ListAdminAgentComponent implements OnInit{

  response: AdministrativeAgentResponse[] = [];

  constructor(
    private adminService: AdministrativeAgentService,
    private toastr: ToastrService,
    private router: Router
  ) {
  }

  private getAllAdminAgent(){
    this.adminService.getAllAgents().subscribe({
      next: (admins: AdministrativeAgentResponse[]) => {
        this.response = admins
      },
      error: (err) => {
        console.log("Erreur lors de chargement")
      }
    })
  }


  ngOnInit(): void {
    this.getAllAdminAgent();
  }

  editAdmin(admin: any){
    this.router.navigate(['/edit-admin', admin.id])
  }

  deleteAdmin(adminId: number){
    if (confirm('Êtes-vous sûr de vouloir supprimer AdministrativeAgent ?')) {
      this.adminService.deleteAgent({ id: adminId }).subscribe({
        next: () => {
          this.toastr.success("AdministrativeAgent supprimé avec succès");
          this.getAllAdminAgent();
        },
        error: (err) => {
          console.log(err);
          this.toastr.error("Désolé, impossible de supprimer", "Oups !");
        }
      });
    }
  }
}
