import { AuthService } from './shared/service/auth.service';
import { LayoutsModule } from './layouts/layouts.module';
import { MemberModule } from './domain/member/member.module';
import { NotFoundComponent } from './not-found/not-found.component';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutes } from './app.routing';
import { HomeComponent } from './home/home.component';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { PrimengModule } from './shared/modules/primeng/primeng.module';
import { AngularModule } from './shared/modules/angular/angular.module';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NotFoundComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AngularModule,
    AppRoutes,
    LayoutsModule,
    MemberModule,
    PrimengModule
  ],
  providers: [
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
