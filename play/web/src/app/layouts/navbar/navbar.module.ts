import { NavbarRoutes } from './navbar.routing';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar.component';

@NgModule({
  imports: [
    CommonModule,
    NavbarRoutes
  ],
  declarations: [NavbarComponent
]
})
export class NavbarModule { }
