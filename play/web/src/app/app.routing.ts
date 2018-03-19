import { HomeComponent } from './home/home.component';
import { Routes, RouterModule } from '@angular/router';
import { NotFoundComponent } from './not-found/not-found.component';

export const routes: Routes = [
  { path: 'home', component: HomeComponent, data: { title: 'Home' } },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', component: NotFoundComponent, data: { title: '404' } },
];

export const AppRoutes = RouterModule.forRoot(routes, { enableTracing: true });
