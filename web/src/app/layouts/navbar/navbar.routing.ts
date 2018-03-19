import { Routes, RouterModule } from '@angular/router';
import { NavbarComponent } from './navbar.component';

const routes: Routes = [
  { path: '', component: NavbarComponent, outlet: 'navbar' },
];

export const NavbarRoutes = RouterModule.forChild(routes);
