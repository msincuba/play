import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DataTableModule } from 'primeng/datatable';
import {ButtonModule} from 'primeng/button';

@NgModule({
  imports: [
    CommonModule,
    DataTableModule,
    ButtonModule
  ],
  declarations: [

  ],
  providers: [

  ],
  exports: [
    DataTableModule,
    ButtonModule
  ]
})
export class PrimengModule { }
