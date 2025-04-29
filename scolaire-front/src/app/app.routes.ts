import {Routes} from '@angular/router';
import {SidebarComponent} from './shared/component/sidebar/sidebar.component';
import {ListStudentsComponent} from './business/students/list-students/list-students.component';
import {FormStudentComponent} from './business/students/form-student/form-student.component';
import {authGuard} from './services/utils/guard/guard.guard';
import { LoginComponent } from './shared/pages/login/login.component';
import {FormKindComponent} from './business/kind/form-kind/form-kind.component';
import {ListKindsComponent} from './business/kind/list-kinds/list-kinds.component';
import {ListProgramComponent} from './business/program/list-program/list-program.component';
import {FormProgramComponent} from './business/program/form-program/form-program.component';
import {ListSectorComponent} from './business/sector/list-sector/list-sector.component';
import {FormSectorComponent} from './business/sector/form-sector/form-sector.component';
import {ListSubjectComponent} from './business/subject/list-subject/list-subject.component';
import {FormSubjectComponent} from './business/subject/form-subject/form-subject.component';
import {ListHalfYearlyComponent} from './business/half-yearly/list-half-yearly/list-half-yearly.component';
import {FormHalfYearlyComponent} from './business/half-yearly/form-half-yearly/form-half-yearly.component';
import {ListClasseComponent} from './business/classe/list-classe/list-classe.component';
import {FormClasseComponent} from './business/classe/form-classe/form-classe.component';
import {SubjectClasseComponent} from './business/classe/subject-classe/subject-classe.component';
import {ListTeacherComponent} from './business/teacher/list-teacher/list-teacher.component';
import {FormTeacherComponent} from './business/teacher/form-teacher/form-teacher.component';
import {ListCourseComponent} from './business/course/list-course/list-course.component';
import {FormCourseComponent} from './business/course/form-course/form-course.component';
import {CourseTeacherComponent} from './business/teacher/course-teacher/course-teacher.component';
import {ListAdminAgentComponent} from './business/administrativeAgent/list-admin-agent/list-admin-agent.component';
import {FormAdminAgentComponent} from './business/administrativeAgent/form-admin-agent/form-admin-agent.component';
import {ListSessionComponent} from './business/session/list-session/list-session.component';
import {FormSessionComponent} from './business/session/form-session/form-session.component';
import {ListRegistrationComponent} from './business/registration/list-registration/list-registration.component';
import {FormRegistrationComponent} from './business/registration/form-registration/form-registration.component';

export const routes: Routes = [

  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    component: SidebarComponent,
    canActivate: [authGuard],
    children: [
      {
        path: 'students',
        component: ListStudentsComponent,
        canActivate: [authGuard],
      },
      {
        path: 'add-student',
        component: FormStudentComponent,
        canActivate: [authGuard],
      },
      {
        path: 'edit-student/:studentId',
        component: FormStudentComponent,
        canActivate: [authGuard]
      },
      {
        path: 'add-kind',
        component: FormKindComponent,
        canActivate: [authGuard]
      },
      {
        path: 'kinds',
        component: ListKindsComponent,
        canActivate: [authGuard]
      },
      {
        path: 'edit-kind/:kindId',
        component: FormKindComponent,
        canActivate: [authGuard]
      },
      {
        path: 'programs',
        component: ListProgramComponent,
        canActivate: [authGuard]
      },
      {
        path: 'add-program',
        component: FormProgramComponent,
        canActivate: [authGuard]
      },
      {
        path: 'edit-program/:programId',
        component: FormProgramComponent,
        canActivate: [authGuard]
      },
      {
        path: 'sectors',
        component: ListSectorComponent,
        canActivate: [authGuard]
      },
      {
        path: 'add-sector',
        component: FormSectorComponent,
        canActivate: [authGuard]
      },
      {
        path: 'edit-sector/:sectorId',
        component: FormSectorComponent,
        canActivate: [authGuard]
      },

      {
        path: 'subjects',
        component: ListSubjectComponent,
        canActivate: [authGuard]
      },
      {
        path: 'add-subject',
        component: FormSubjectComponent,
        canActivate: [authGuard]
      },
      {
        path: 'edit-subject/:subjectId',
        component: FormSubjectComponent,
        canActivate: [authGuard]
      },
      {
        path: 'half',
        component: ListHalfYearlyComponent,
        canActivate: [authGuard]
      },
      {
        path: 'add-half',
        component: FormHalfYearlyComponent,
        canActivate: [authGuard]
      },
      {
        path: 'edit-half/:halfId',
        component: FormHalfYearlyComponent,
        canActivate: [authGuard]
      },
      {
        path: 'classes',
        component: ListClasseComponent,
        canActivate: [authGuard]
      },
      {
        path: 'add-classe',
        component: FormClasseComponent,
        canActivate: [authGuard]
      },
      {
        path: 'edit-classe/:classeId',
        component: FormClasseComponent,
        canActivate: [authGuard]
      },
      {
        path: 'assignSubjectToClass',
        component: SubjectClasseComponent,
        canActivate: [authGuard]
      },

      {
        path: 'teachers',
        component: ListTeacherComponent,
        canActivate: [authGuard]
      },
      {
        path: 'add-teacher',
        component: FormTeacherComponent,
        canActivate: [authGuard]
      },
      {
        path: 'edit-teacher/:teacherId',
        component: FormTeacherComponent,
        canActivate: [authGuard]
      },
      {
        path: 'courses',
        component: ListCourseComponent,
        canActivate: [authGuard]
      },
      {
        path: 'add-course',
        component: FormCourseComponent,
        canActivate: [authGuard]
      },
      {
        path: 'edit-course/:courseId',
        component: FormCourseComponent,
        canActivate: [authGuard]
      },
      {
        path: 'assignCourseToTeacher',
        component: CourseTeacherComponent,
        canActivate: [authGuard]
      },

      {
        path: 'admin',
        component: ListAdminAgentComponent,
        canActivate: [authGuard]
      },
      {
        path: 'add-admin',
        component: FormAdminAgentComponent,
        canActivate: [authGuard]
      },
      {
        path: 'edit-admin/:adminId',
        component: FormAdminAgentComponent,
        canActivate: [authGuard]
      },

      {
        path: 'sessions',
        component: ListSessionComponent,
        canActivate: [authGuard]
      },
      {
        path: 'add-session',
        component: FormSessionComponent,
        canActivate: [authGuard]
      },
      {
        path: 'edit-session/:sessionId',
        component: FormAdminAgentComponent,
        canActivate: [authGuard]
      },

      {
        path: 'registrations',
        component: ListRegistrationComponent,
        canActivate: [authGuard]
      },
      {
        path: 'add-registration',
        component: FormRegistrationComponent,
        canActivate: [authGuard]
      },
      {
        path: 'edit-registration/:registrationId',
        component: FormRegistrationComponent,
        canActivate: [authGuard]
      },

    ]
  },
];
