import {Component, OnInit} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {Router, RouterLink} from '@angular/router';
import {ProgramsService} from '../../../services/services/programs.service';
import {ProgramResponse} from '../../../services/models/program-response';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-list-program',
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './list-program.component.html',
  styleUrl: './list-program.component.css'
})
export class ListProgramComponent implements OnInit{

  response: ProgramResponse[] = [];

  constructor(
    private programService: ProgramsService,
    private toastr: ToastrService,
    private router: Router
  ) {
  }
  ngOnInit(): void {
    this.getAllPrograms()
  }

  private getAllPrograms(){
    this.programService.getAllPrograms()
      .subscribe({
        next: (programs: ProgramResponse[]) => {
          this.response = programs
        },
        error: (err) => {
          console.log("Erreur lors de chargement")
        }
      });
  }

  editProgram(program: any){
    this.router.navigate(['/edit-program', program.id])
  }

  deleteProgram(programId: number){
    if (confirm('Êtes-vous sûr de vouloir supprimer cet Program ?')) {
      this.programService.deleteProgramById({ id: programId }).subscribe({
        next: () => {
          this.toastr.success("program supprimé avec succès");
          this.getAllPrograms();
        },
        error: (err) => {
          console.log(err);
          this.toastr.error("Désolé, impossible de supprimer", "Oups !");
        }
      });
    }
  }

}
