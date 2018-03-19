import { MemberService } from './member.service';
import { AngularModule } from './../../shared/modules/angular/angular.module';
import { FormsModule } from '@angular/forms';
import { MemberComponent } from './member/member.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MemberListComponent } from './member-list/member-list.component';
import { ContactComponent } from './contact/contact.component';
import { DependantComponent } from './dependant/dependant.component';
import { EmploymentComponent } from './employment/employment.component';
import { MemberRoutes } from './member.routing';
import { PrimengModule } from '../../shared/modules/primeng/primeng.module';

@NgModule({
  imports: [
    PrimengModule,
    AngularModule,
    MemberRoutes
  ],
  exports: [

  ],
  declarations: [
    MemberComponent,
    MemberListComponent,
    ContactComponent,
    DependantComponent,
    EmploymentComponent
  ],
  providers: [
    MemberService
  ]
})
export class MemberModule { }
