import {springHttpClient} from "ra-data-spring-rest";
import {fetchUtils} from "react-admin";

const httpClient = springHttpClient(async (url, options = {}) => {

    return fetchUtils.fetchJson(url, options)
        .then(response => {
            console.log(response.status);
            console.log(response.body);
            console.log(response.json);

/*
{
  "_embedded" : {
    "terms" : [ ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/terms?page=0&size=10&sort=id%2CASC"
    },
    "profile" : {
      "href" : "http://localhost:8080/profile/terms"
    }
  },
  "page" : {
    "size" : 10,
    "totalElements" : 0,
    "totalPages" : 0,
    "number" : 0
  }
}
 */
            let translatedResponse =
                {
                    json:
                        {
                            content: response.json._embedded.terms,
                            totalElements: response.json.page.totalElements
                        }
                };

            console.log(translatedResponse);

            return translatedResponse;
            // is it worth trying to convert
            // OR
            // just add new endpoints that return the correct format?
            // { data: {Record[]}, total: {int} }
        })
});

export default httpClient;