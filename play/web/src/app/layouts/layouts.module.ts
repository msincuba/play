import { FooterModule } from './footer/footer.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarModule } from './navbar/navbar.module';

@NgModule({
  imports: [
    NavbarModule,
    FooterModule
  ],
  declarations: [

  ],
  providers: [

  ],
  exports: [
    NavbarModule,
    FooterModule
  ]
})
export class LayoutsModule { }
