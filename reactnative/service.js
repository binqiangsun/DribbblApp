'use strict';

import qs from 'query-string'


let service_params

let Service = {
    init(params) {
        service_params = params
    },

    get(url, params) {
        if (!service_params) {
            console.log('Service.init should be called before get')
            return
        }

    	if (params) {
    		url = `${url}?${qs.stringify(params)}`
    	}

    	return fetch(url, {
    		method: 'GET',
    		headers: service_params.headers,
        }).then(res => res.json())
    },
}

export default Service
