import * as React from "react";
import { Admin, Resource, ListGuesser } from 'react-admin';
import {springDataProvider, springHttpClient} from 'ra-data-spring-rest';
import httpClient from "./FetchClient";

const dataProvider = springDataProvider('http://localhost:8080', httpClient);

const App = () => (
    <Admin dataProvider={dataProvider}>
        <Resource name="terms" list={ListGuesser} />
    </Admin>
    );

export default App;