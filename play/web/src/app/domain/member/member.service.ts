import { Member } from './member';
import { createRequestOption } from './../../shared/model/request-util';
import { SERVER_API_URL } from './../../app.constants';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BaseService } from '../../shared/model/base-service';

@Injectable()
export class MemberService extends BaseService {

  private resourceUrl = SERVER_API_URL + 'api/bank-accounts';

  constructor(private http: HttpClient) {
    super();
   }

  query(req?: any) {
    const options = createRequestOption(req);
    return this.http.get(this.resourceUrl, BaseService.httpOptions);
  }

  find(id: number) {
    return this.http.get(`${this.resourceUrl}/${id}`, BaseService.httpOptions);
  }

  create(member: Member) {

  }
}
