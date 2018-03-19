import { RequestOptions, Headers } from '@angular/http';
import { HttpHeaders } from '@angular/common/http';

export class BaseService {

  public static httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': 'my-auth-token'
    })
  };

  requestOptions: RequestOptions;

  static getHeaders(): Headers {
      const headers = new Headers({
          'Content-Type': 'application/json; charset=utf-8'
      });
      return headers;
  }

  getOptions(method, url, body, params) {
      return this.requestOptions = new RequestOptions({
          method: method,
          url: url,
          headers: BaseService.getHeaders(),
          body: body,
          search: params
      });
  }
}
