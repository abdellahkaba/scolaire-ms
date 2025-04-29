import {Component, OnInit} from '@angular/core';
import {SessionResponse} from '../../../services/models/session-response';
import {SessionsService} from '../../../services/services/sessions.service';
import {ToastrService} from 'ngx-toastr';
import {Router, RouterLink} from '@angular/router';
import {ProgramResponse} from '../../../services/models/program-response';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-list-session',
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './list-session.component.html',
  styleUrl: './list-session.component.css'
})
export class ListSessionComponent implements OnInit{

  response: SessionResponse[] = [];

  constructor(
    private sessionService: SessionsService,
    private toastr: ToastrService,
    private router: Router
  ) {
  }
  ngOnInit(): void {
    this.getAllSessions();
  }


  private getAllSessions(){
    this.sessionService.getAllSessions()
      .subscribe({
        next: (sessions: SessionResponse[]) => {
          this.response = sessions
        },
        error: (err) => {
          console.log("Erreur lors de chargement")
        }
      });
  }

  editSession(session: any){
    this.router.navigate(['/edit-session', session.id])
  }

  deleteSession(sessionId: number){
    if (confirm('Êtes-vous sûr de vouloir supprimer cette Session ?')) {
      this.sessionService.deleteSessionById({ id: sessionId }).subscribe({
        next: () => {
          this.toastr.success("Session supprimée avec succès");
          this.getAllSessions();
        },
        error: (err) => {
          console.log(err);
          this.toastr.error("Désolé, impossible de supprimer", "Oups !");
        }
      });
    }
  }


}
