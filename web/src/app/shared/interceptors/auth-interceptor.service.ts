import { TokenService } from './../service/token.service';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import {
    HttpInterceptor,
    HttpRequest,
    HttpHandler,
    HttpSentEvent,
    HttpHeaderResponse,
    HttpProgressEvent,
    HttpResponse,
    HttpUserEvent } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private tokenService: TokenService, private router: Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler):
    Observable<HttpSentEvent | HttpHeaderResponse | HttpProgressEvent | HttpResponse<any> | HttpUserEvent<any>> {
    let authReq = req;
    const token = this.tokenService.getToken();
    if ( token ) {
      authReq = req.clone({ headers: req.headers.set(TokenService.TOKEN, 'Bearer ' + token) });
    } else {
      const navigatedToLogin: Promise<boolean> = this.router.navigate(['login']);
      navigatedToLogin.then(
        fullfilled => {
          console.log('fulfilled? ' + fullfilled);
        },
        rejected => {
          console.log('rejected? ' + rejected);
        }
      );
      console.log('navigated to login');
    }
    return next.handle(authReq);
  }


}
