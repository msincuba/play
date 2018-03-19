import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './footer.component';
import { FooterRoutes } from './footer.routing';

@NgModule({
  imports: [
    CommonModule,
    FooterRoutes
  ],
  declarations: [FooterComponent]
})
export class FooterModule { }
