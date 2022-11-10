import {
    CREATE,
    DELETE,
    DELETE_MANY, fetchUtils,
    GET_LIST,
    GET_MANY,
    GET_MANY_REFERENCE,
    GET_ONE,
    UPDATE,
    UPDATE_MANY
} from "react-admin";

import _ from 'lodash';

function pageParams(params) {
    return {
        ...params.filter,
        page: params.pagination.page - 1,
        size: params.pagination.perPage,
        sort: `${params.sort.field},${params.sort.order}`
    };
}

function pageFormat(response) {
    console.log("PageFormat of " + response.json);
    return {
        data: response.json.content,
        total: parseInt(response.json.totalElements, 10)
    };
}

/**
 * Maps react-admin queries to a REST API implemented using Spring Rest
 *
 * @example
 * GET_LIST             => GET http://my.api.url/posts?keyword=&page=0&size=10&sort=id,asc
 * GET_ONE              => GET http://my.api.url/posts/123
 * GET_MANY             => GET http://my.api.url/posts?id=1234&id=5678
 * GET_MANY_REFERENCE   => GET http://my.api.url/comments?postId=&page=0&size=10&sort=id,asc
 * CREATE               => POST http://my.api.url/posts
 * UPDATE               => PUT http://my.api.url/posts/123
 * UPDATE_MANY          => multiple call UPDATE
 * DELETE               => DELETE http://my.api.url/posts/123
 * DELETE_MANY          => multiple call DELETE
 */
export default (apiUrl, httpClient = fetch) => {
    let dataProvider = (type, resource, params) => {
        let url = `${apiUrl}/${resource}`,
            options = {},
            format = response => {
                console.log(response);
                console.log(response.body);
                console.log(response.json);
                return {data: response.json}
            }
//            format = response => ({data: response.json});
        switch (type) {
            case GET_LIST:
                options.method = 'GET';
                options.params = pageParams(params);
                //format = pageFormat;
                format = response => {
                    console.log(response);
                    console.log(response.body);
                    console.log(response.json);

                let mapped = _.map(response.json._embedded.terms, (item) => {
                    let id = _.last(_.split(item._links.self.href, '/'))
                   return {
                        id: `${id}`,
                        title: `${item.title}`,
                        startDate: `${item.startDate}`,
                        endDate: `${item.endDate}`,
                   } ;
                });

                    let stuff = {data : mapped, total: response.json.page.totalElements};
                    console.log(stuff);

                    return stuff;
                }
                console.log("GET_LIST for " + resource);
                if (resource === "terms") {
                    url = `${apiUrl}/${resource}`
                }
                console.log("URL = " + url);
                 return fetchUtils.fetchJson(url, options).then(format);
                //     .then(response => {
                //         console.log(response.status);
                //         console.log(response.body);
                //         console.log(response.json);
                //         let stuff = {data: response.json};
                //         console.log(stuff);
                //         return stuff;
                //     })

                // break;
            case GET_ONE:
                options.method = 'GET';
                url += `/${params.id}`;
                format = response => {
                    let item = response.json;
                    console.log("GET_ONE = " + item);

                    return {
                        data: {
                            id: `${params.id}`,
                            title: `${item.title}`,
                            startDate: `${item.startDate}`,
                            endDate: `${item.endDate}`
                        }
                    };
                };
                return fetchUtils.fetchJson(url, options).then(format);
//                break;
            case GET_MANY:
                options.method = 'GET';
                options.params = {id: params.ids};
                break;
            case GET_MANY_REFERENCE:
                options.method = 'GET';
                options.params = pageParams(params);
                options.params[params.target] = params.id;
                format = pageFormat;
                break;
            case CREATE:
                console.log("CREATE for " + resource);
                console.log("URL = " + url);
                options.method = 'POST';
                console.log("PARAMS DATA = " + params.data["title"]);
                console.log("PARAMS DATA = " + params.data["startDate"]);
                console.log("PARAMS DATA = " + params.data["endDate"]);
                 options.body = {
                     title: `${params.data["title"]}`,
                     startDate: `${params.data["startDate"]}`,
                     endDate: `${params.data["endDate"]}`,
                 };
                // options.body = params.data;
                const requestHeaders = new Headers({
                        Accept: 'application/json',
                    });
                requestHeaders.set('Content-Type', 'application/json');

                return fetchUtils.fetchJson(url, {
                    body: JSON.stringify(options.body),
                    headers: requestHeaders,
                    method: "POST"
                }).then(
                    response => {
                        console.log("CREATE RESPONSE = " + response.json);
                        let id = _.last(_.split(response.json._links.self.href, '/'))

                        let returnValue = { data: {
                                id: `${id}`,
                                title: `${response.json.title}`,
                                startDate: `${response.json.startDate}`,
                                endDate: `${response.json.endDate}`,
                            } };
                        console.log(returnValue);

                        return returnValue;
                    }
                );
                // break;
            case UPDATE:
                url += `/${params.id}`;
                options.method = 'PUT';
//                options.body = params.data;

                console.log("UPDATE for " + resource);
                console.log("URL = " + url);
                console.log("PARAMS DATA = " + params.data["id"]);
                console.log("PARAMS DATA = " + params.data["title"]);
                console.log("PARAMS DATA = " + params.data["startDate"]);
                console.log("PARAMS DATA = " + params.data["endDate"]);
                options.body = {
                    //id: `${params.data["id"]}`,
                    title: `${params.data["title"]}`,
                    startDate: `${params.data["startDate"]}`,
                    endDate: `${params.data["endDate"]}`,
                };
                // options.body = params.data;
                const requestHeaders2 = new Headers({
                    Accept: 'application/json',
                });
                requestHeaders2.set('Content-Type', 'application/json');

                return fetchUtils.fetchJson(url, {
                    body: JSON.stringify(options.body),
                    headers: requestHeaders2,
                    method: "PUT"
                }).then(
                    response => {
                        console.log("CREATE UPDATE RESPONSE = " + response.json);
                        let id = _.last(_.split(response.json._links.self.href, '/'))

                        let returnValue = { data: {
                                id: `${id}`,
                                title: `${response.json.title}`,
                                startDate: `${response.json.startDate}`,
                                endDate: `${response.json.endDate}`,
                            } };
                        console.log(returnValue);

                        return returnValue;
                    }
                );
            // break;
            case UPDATE_MANY:
                //multiple call UPDATE
                return Promise.all(params.ids.map(id => dataProvider(UPDATE, resource, {id, data: params.data})))
                    .then(response => ({data: response.map(item => item.data)}));
            case DELETE:
                url += `/${params.id}`;
                options.method = 'DELETE';
                break;
            case DELETE_MANY:
                //multiple call DELETE
                return Promise.all(params.ids.map(id => dataProvider(DELETE, resource, {id})))
                    .then(response => ({data: response.map(item => item.data)}));
            default:
                throw new Error(`unknown type [${type}]`);
        }
        return httpClient(url, options).then(format);
    };
    return dataProvider;
};
