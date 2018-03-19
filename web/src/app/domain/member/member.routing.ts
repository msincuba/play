import { EmploymentComponent } from './employment/employment.component';
import { DependantComponent } from './dependant/dependant.component';
import { MemberComponent } from './member/member.component';
import { MemberListComponent } from './member-list/member-list.component';
import { Routes, RouterModule } from '@angular/router';
import { ContactComponent } from './contact/contact.component';

const routes: Routes = [
  { path: 'members', component: MemberListComponent, data: { title: 'Members' } },
  { path: 'members/', redirectTo: 'members', pathMatch: 'full' },
  { path: 'members/:id', component: MemberComponent, data: { title: 'Member' } , pathMatch: 'full',
    children: [
      { path: 'contact', component: ContactComponent, data: { title: 'Member Contact' }  },
      { path: 'dependant', component: DependantComponent, data: { title: 'Member Dependants' }  },
      { path: 'employment', component: EmploymentComponent, data: { title: 'Member Employment' }  },
    ]
  },
  { path: 'members/:id/contact', component: ContactComponent, data: { title: 'Member Contact' }  },

];

export const MemberRoutes = RouterModule.forChild(routes);
