import * as React from "react";
import { Admin, Resource, ListGuesser } from 'react-admin';
import {springDataProvider} from 'ra-data-spring-rest';
import FetchClient from "./FetchClient";
const dataProvider = springDataProvider('http://localhost:8080', httpClient);

const httpClient = FetchClient();

const App = () => (
    <Admin dataProvider={dataProvider}>
        <Resource name="terms" list={ListGuesser} />
    </Admin>
    );

export default App;