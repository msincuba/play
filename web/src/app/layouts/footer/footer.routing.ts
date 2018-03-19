import { FooterComponent } from './footer.component';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: '', component: FooterComponent, outlet: 'footer' },
];

export const FooterRoutes = RouterModule.forChild(routes);
