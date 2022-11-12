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
const fetch = (apiUrl, httpClient = fetch) => {
    let dataProvider = (type, resource, params) => {
        let url = `${apiUrl}/${resource}`,
            options = {},
            format = response => {
                console.log(response);
                console.log(response.body);
                console.log(response.json);
                return {data: response.json}
            }
        switch (type) {
            case GET_LIST:
                console.log("GET_LIST for " + resource);
                console.log("URL = " + url);

                options.method = 'GET';
                options.params = pageParams(params);



                switch (resource) {
                    case "terms":
                        format = response => {
                            console.log(response);
                            console.log(response.body);
                            console.log(response.json);

                            let mapped = _.map(response.json._embedded.terms, (item) => {
                                let id = _.last(_.split(item._links.self.href, '/'))

                                item["id"] = id;

                                // return {
                                //     id: `${id}`,
                                //     title: `${item.title}`,
                                //     startDate: `${item.startDate}`,
                                //     endDate: `${item.endDate}`,
                                // };
                                return item;
                            });

                            let stuff = {data : mapped, total: response.json.page.totalElements};
                            console.log(stuff);

                            return stuff;
                        }
                        break;
                    case "instructors":
                        format = response => {
                            console.log(response);
                            console.log(response.body);
                            console.log(response.json);

                            let mapped = _.map(response.json._embedded.instructors, (item) => {
                                let id = _.last(_.split(item._links.self.href, '/'))
                                item["id"] = id;

                                // return {
                                //     id: `${id}`,
                                //     name: `${item.name}`,
                                //     phoneNumber: `${item.phoneNumber}`,
                                //     emailAddress: `${item.emailAddress}`,
                                // };
                                return item;
                            });

                            let stuff = {data : mapped, total: response.json.page.totalElements};
                            console.log(stuff);

                            return stuff;
                        }
                        break;
                    case "assessments":
                        format = response => {
                            console.log(response);
                            console.log(response.body);
                            console.log(response.json);

                            let mapped = _.map(response.json._embedded.assessments, (item) => {
                                let id = _.last(_.split(item._links.self.href, '/'))
                                item["id"] = id;
                                // return {
                                //     id: `${id}`,
                                //     title: `${item.title}`,
                                //     startDate: `${item.startDate}`,
                                //     endDate: `${item.endDate}`,
                                //     performance: `${item.performance}` === 'true' ? true: false
                                // };
                                return item;
                            });

                            let stuff = {data : mapped, total: response.json.page.totalElements};
                            console.log(stuff);

                            return stuff;
                        }
                        break;
                    case "courses":
                        format = response => {
                            console.log(response.json);

                            let mapped = _.map(response.json._embedded.courses, (item) => {
                                let id = _.last(_.split(item._links.self.href, '/'))
                                item["id"] = id;
                                // return {
                                //     id: `${id}`,
                                //     title: `${item.title}`,
                                //     status: `${item.status}`,
                                //     startDate: `${item.startDate}`,
                                //     endDate: `${item.endDate}`
                                // };
                                return item;
                            });

                            let stuff = {data : mapped, total: response.json.page.totalElements};
                            console.log("console stuff");
                            console.log(stuff);

                            return stuff;
                        }
                        break;
                    default:
                        break;
                }
                break;
                //return fetchUtils.fetchJson(url, options).then(format);
            case GET_ONE:
                options.method = 'GET';
                url += `/${params.id}`;
                format = response => {
                    let item = response.json;
                    console.log("GET_ONE = " + item);

                    let data = item;

                    data["id"] = params.id;

                    switch (resource) {
                        case "terms":
                            break;
                        case "instructors":
                            break;
                        case "assessments":
                            data.performance = `${item.performance}` === 'true';
                            break;
                        case "courses":
                            console.log("GET ONE courses");
                            console.log(item);
                            let notes = _.map(item.notes, (it) => {
                               return {
                                   text: it
                               };
                            });
                            console.log(notes);
                            data.notes = notes;
                            //
                            //
                            // data =
                            //     {
                            //         id: `${params.id}`,
                            //         title: `${item.title}`,
                            //         status: `${item.status}`,
                            //         startDate: `${item.startDate}`,
                            //         endDate: `${item.endDate}`,
                            //         notes: notes
                            //     }

                            console.log("GET_ONE course fixed");
                            console.log(data);
                            break;
                        default:
                            break;
                    }

                    return {
                        data: data
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
                console.log(params.data);
                options.method = 'POST';

                switch (resource) {
                    case "terms":
                        console.log("PARAMS DATA = " + params.data["title"]);
                        console.log("PARAMS DATA = " + params.data["startDate"]);
                        console.log("PARAMS DATA = " + params.data["endDate"]);
                        options.body = {
                            title: `${params.data["title"]}`,
                            startDate: `${params.data["startDate"]}`,
                            endDate: `${params.data["endDate"]}`,
                        };
                        break;
                    case "instructors":
                        console.log("PARAMS DATA = " + params.data["name"]);
                        console.log("PARAMS DATA = " + params.data["phoneNumber"]);
                        console.log("PARAMS DATA = " + params.data["emailAddress"]);
                        options.body = {
                            name: `${params.data["name"]}`,
                            phoneNumber: `${params.data["phoneNumber"]}`,
                            emailAddress: `${params.data["emailAddress"]}`,
                        };
                        break;
                    case "assessments":
                        options.body = {
                            title: `${params.data["title"]}`,
                            startDate: `${params.data["startDate"]}`,
                            endDate: `${params.data["endDate"]}`,
                            performance: `${params.data["performance"]}` === 'true' ? true: false
                        };
                        break;
                    case "courses":
                        console.log("PARAMS DATA title = " + params.data["title"]);
                        console.log("PARAMS DATA startDate = " + params.data["startDate"]);
                        console.log("PARAMS DATA endDate = " + params.data["endDate"]);
                        console.log("PARAMS DATA notes = " + params.data["notes"]);

                        let notes = _.map(params.data["notes"], (item) => {
                            return item.text
                        })
                        console.log("notes = " + notes);

                        options.body = {
                            title: `${params.data["title"]}`,
                            status: `${params.data["status"]}`,
                            startDate: `${params.data["startDate"]}`,
                            endDate: `${params.data["endDate"]}`,
                            notes: notes
                        };
                        break;
                    default:
                        break;
                }
                const requestHeaders = new Headers({
                        Accept: 'application/json',
                    });
                requestHeaders.set('Content-Type', 'application/json');

                console.log("crafted body stringified");
                console.log(JSON.stringify(options.body));

                return fetchUtils.fetchJson(url, {
                    body: JSON.stringify(options.body),
                    headers: requestHeaders,
                    method: "POST"
                }).then(
                    response => {
                        console.log("CREATE RESPONSE = " + response.json);
                        let id = _.last(_.split(response.json._links.self.href, '/'))

                        let returnValue = {};

                        switch (resource) {
                            case "terms":
                                returnValue = { data: {
                                        id: `${id}`,
                                        title: `${response.json.title}`,
                                        startDate: `${response.json.startDate}`,
                                        endDate: `${response.json.endDate}`,
                                    } };
                                break;
                            case "instructors":
                                returnValue =
                                    { data: {
                                        id: `${id}`,
                                        name: `${response.json.name}`,
                                        phoneNumber: `${response.json.phoneNumber}`,
                                        emailAddress: `${response.json.emailAddress}`
                                    } };
                                break;
                            case "assessments":
                                returnValue = { data: {
                                        id: `${id}`,
                                        title: `${response.json.title}`,
                                        startDate: `${response.json.startDate}`,
                                        endDate: `${response.json.endDate}`,
                                        performance: `${response.json.performance}` === 'true' ? true: false
                                    } };
                                break;
                            case "courses":
                                returnValue = { data: {
                                        id: `${id}`,
                                        title: `${response.json.title}`,
                                        status: `${response.json.status}`,
                                        startDate: `${response.json.startDate}`,
                                        endDate: `${response.json.endDate}`,
                                    } };
                                break;
                            default:
                                break;
                        }

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
                switch (resource) {
                    case "terms":
                        options.body = {
                            title: `${params.data["title"]}`,
                            startDate: `${params.data["startDate"]}`,
                            endDate: `${params.data["endDate"]}`,
                        };
                        break;
                    case "instructors":
                        options.body = {
                            name: `${params.data["name"]}`,
                            phoneNumber: `${params.data["phoneNumber"]}`,
                            emailAddress: `${params.data["emailAddress"]}`,
                        };
                        break;
                    case "assessments":
                        options.body = {
                            title: `${params.data["title"]}`,
                            startDate: `${params.data["startDate"]}`,
                            endDate: `${params.data["endDate"]}`,
                            performance: `${params.data["performance"]}`
                        };
                        break;
                    case "courses":
                        let notes = _.map(params.data["notes"], (item) => {
                            return item.text
                        })
                        console.log("notes = " + notes);

                        options.body = {
                            title: `${params.data["title"]}`,
                            status: `${params.data["status"]}`,
                            startDate: `${params.data["startDate"]}`,
                            endDate: `${params.data["endDate"]}`,
                            notes: notes
                        };
                        break;
                    default:
                        break;
                }
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

                        let returnValue = {};

                        switch (resource) {
                            case "terms":
                                returnValue = { data: {
                                        id: `${id}`,
                                        title: `${response.json.title}`,
                                        startDate: `${response.json.startDate}`,
                                        endDate: `${response.json.endDate}`,
                                    } };
                                break;
                            case "instructors":
                                returnValue =
                                    { data: {
                                        id: `${id}`,
                                        name: `${response.json.name}`,
                                        phoneNumber: `${response.json.phoneNumber}`,
                                        emailAddress: `${response.json.emailAddress}`
                                    } };
                                break;
                            case "assessments":
                                returnValue = { data: {
                                        id: `${id}`,
                                        title: `${response.json.title}`,
                                        startDate: `${response.json.startDate}`,
                                        endDate: `${response.json.endDate}`,
                                        performance: `${response.json.performance}`
                                    } };
                                break;
                            case "courses":
                                returnValue = { data: {
                                        id: `${id}`,
                                        title: `${response.json.title}`,
                                        status: `${response.json.status}`,
                                        startDate: `${response.json.startDate}`,
                                        endDate: `${response.json.endDate}`
                                    } };
                                break;
                            default:
                                break;
                        }

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

export default fetch;