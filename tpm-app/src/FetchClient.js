import {springHttpClient} from "ra-data-spring-rest";
import {fetchUtils} from "react-admin";

const httpClient = springHttpClient(async (url, options = {}) => {

    return fetchUtils.fetchJson(url, options)
        .then(response => {
            console.log(response.status);
            console.log(response.body);
            console.log(response.json);

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
        })
});

export default httpClient;