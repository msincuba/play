import { URLSearchParams, BaseRequestOptions } from '@angular/http';

export const createRequestOption = (req?: any): URLSearchParams => {
    const params: URLSearchParams = new URLSearchParams();
    if (req) {
        params.set('page', req.page);
        params.set('size', req.size);
        if (req.sort) {
            params.paramsMap.set('sort', req.sort);
        }
        params.set('query', req.query);
        params.set('filter', req.filter);
    }
    return params;
};
