import {Component, OnInit} from '@angular/core';
import {AdministrativeAgentRequest} from '../../../services/models/administrative-agent-request';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {AdministrativeAgentService} from '../../../services/services/administrative-agent.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-form-admin-agent',
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './form-admin-agent.component.html',
  styleUrl: './form-admin-agent.component.css'
})
export class FormAdminAgentComponent implements OnInit{
  errorMsg: Array<string> = []
  isEditMode = false;

  adminRequest: AdministrativeAgentRequest = {
    id: undefined,
    firstName: '',
    lastName: '',
    emailPro: '',
    emailPerso: '',
    phoneNumber: '',
    address: '',
    archive: false
  }

  constructor(
    private adminService: AdministrativeAgentService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toastrService: ToastrService
  ) {
  }

  ngOnInit(): void {
    this.loadAdminAgent()
  }

  loadAdminAgent(){
    const adminId = this.activatedRoute.snapshot.params['adminId']
    if (adminId){
      this.isEditMode = true
      this.adminService.getAgent({
        'id': adminId
      }).subscribe({
        next: (admin) => {
          this.adminRequest = {
            id: admin.id,
            firstName: admin.firstName as string,
            lastName: admin.lastName as string,
            emailPro: admin.emailPro as string,
            emailPerso: admin.emailPerso as string,
            phoneNumber: admin.phoneNumber as string,
            address: admin.address as string,
            archive: false
          }
        }
      })
    }
  }

  saveAdminAgent(){
    if (this.isEditMode){
      this.updateAdminAgent();
    }else {
      this.addAdminAgent();
    }
  }

  private addAdminAgent(){
    this.adminService.addAgent({
      body: this.adminRequest
    }).subscribe({
      next: data => {
        this.toastrService.success("Administrative Agent ajouté");
        this.router.navigate(['/admin']);
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

  private updateAdminAgent(){
    this.adminService.updateAgent({
      body: this.adminRequest
    }).subscribe({
      next: data => {
        this.toastrService.success("Administrative Agent mis à jour");
        this.router.navigate(['/admin']);
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
