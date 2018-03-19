import { Injectable } from '@angular/core';

const TOKEN_KEY = 'Authorization';

/**
 * Login token
 *
 * @export
 * @class TokenService
 */
@Injectable()
export class TokenService {

  constructor() { }

  public static TOKEN = TOKEN_KEY;

  /**
   * Clears sessionStorage
   *
   * @memberof TokenService
   */
  logout() {
    sessionStorage.removeItem(TOKEN_KEY);
    sessionStorage.clear();
  }

  /**
   * Saves token to sessionStorage
   *
   * @param {string} token login token
   * @memberof TokenService
   */
  public saveToken(token: string) {
    sessionStorage.removeItem(TOKEN_KEY);
    sessionStorage.setItem(TOKEN_KEY, token);
  }

  /**
   * Gets login token
   *
   * @returns {string} login token
   * @memberof TokenService
   */
  public getToken(): string {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  /**
   * Retrievs token key
   */
  public getTokenKey(): string {
    return TOKEN_KEY;
  }
}
