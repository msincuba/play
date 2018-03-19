import { Injectable } from '@angular/core';


/**
 *
 * Uses Browser sessionStorage introduced by HTML 5. Do not use this if you support ancient browsers.
 *
 * @export
 * @class StorageService
 */
@Injectable()
export class StorageService {

  constructor() { }

  /**
   * Saves value based on key to sessionStorage
   * @param key reference for the value
   * @param value value to save
   */
  public save(key: string, value: any) {
    sessionStorage.setItem(key, value);
  }

  /**
   * Gets stored value for key from sessionStorage
   *
   * @param {string} key reference for the value
   * @returns {*} value stored or null
   * @memberof StorageService
   */
  public getValue(key: string): any {
    return sessionStorage.getItem(key);
  }

  /**
   * Removes item from sessionStorage
   *
   * @param {string} key reference for the value
   * @memberof StorageService
   */
  public delete(key: string) {
    sessionStorage.removeItem(key);
  }

  /**
   * Clears sessionStorage
   *
   * @memberof StorageService
   */
  public deleteAll() {
    sessionStorage.clear();
  }

}
