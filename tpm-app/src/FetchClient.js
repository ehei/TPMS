import {springHttpClient} from "ra-data-spring-rest";
import fetch from 'node-fetch';

const httpClient = springHttpClient(async (url, options = {}) => {
    const [response] = await Promise.all([fetch(url, options)])

    console.log(response.ok);
    console.log(response.status);
    console.log(response.statusText);
    console.log(response.headers.raw());
    console.log(response.headers.get('content-type'));

    return response;
});

export default httpClient;