import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {RegistrationsService} from '../../../services/services/registrations.service';
import {ToastrService} from 'ngx-toastr';
import {RegistrationResponse} from '../../../services/models/registration-response';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-list-registration',
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './list-registration.component.html',
  styleUrl: './list-registration.component.css'
})
export class ListRegistrationComponent implements OnInit{

  response: RegistrationResponse[] = []

  page = 0;
  size = 4;
  pages: any = [];
  message = '';

  constructor(
    private router: Router,
    private registrationService: RegistrationsService,
    private toastr: ToastrService
  ) {
  }
  ngOnInit(): void {
    this.getAllRegistration();
  }

  private getAllRegistration(){
    this.registrationService.getAllRegistrations({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (registrations: RegistrationResponse[]) => {
        this.response = registrations
      },
      error: (err) => {
        console.log("Erreur lors de chargement")
      }
    })
  }

  editRegistration(registration: any){
    this.router.navigate(['/edit-registration', registration.id])
  }

  deleteRegistration(registrationId: number){
    if (confirm('Êtes-vous sûr de vouloir supprimer cette Registration ?')) {
      this.registrationService.deleteRegistrationById({ id: registrationId }).subscribe({
        next: () => {
          this.toastr.success("Registration supprimée avec succès");
          this.getAllRegistration();
        },
        error: (err) => {
          console.log(err);
          this.toastr.error("Désolé, impossible de supprimer", "Oups !");
        }
      });
    }
  }


  // registrationResponse: PageResponseRegistrationResponse = {}
  // private allRegistration() {
  //   this.registrationService.getAllRegistrations({
  //     page: this.page,
  //     size: this.size
  //   })
  //     .subscribe({
  //       next: (registrations) => {
  //         this.registrationResponse.content = registrations;
  //       },
  //       error: (err)  => {
  //         console.log("Erreur de chargement")
  //       }
  //     });
  // }
  //
  // gotToPage(page: number) {
  //   this.page = page;
  //   this.allRegistration();
  // }
  //
  // goToFirstPage() {
  //   this.page = 0;
  //   this.allRegistration();
  // }
  //
  // goToPreviousPage() {
  //   this.page --;
  //   this.allRegistration();
  // }
  //
  // goToLastPage() {
  //   this.page = this.registrationResponse.totalPages as number - 1;
  //   this.allRegistration();
  // }
  //
  // goToNextPage() {
  //   this.page++;
  //   this.allRegistration();
  // }
  //
  // get isLastPage() {
  //   return this.page === this.registrationResponse.totalPages as number - 1;
  // }

}
