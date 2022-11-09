import * as React from "react";
import { Admin, Resource, CreateButton, ListGuesser, ShowGuesser, EditGuesser } from 'react-admin';
import {springDataProvider, springHttpClient} from 'ra-data-spring-rest';
import httpClient from "./FetchClient";

const dataProvider = springDataProvider('http://localhost:8080/api', httpClient);

const App = () => (
    <Admin dataProvider={dataProvider}  >
        <Resource name="terms" list={ListGuesser} show={ShowGuesser} edit={EditGuesser} />
    </Admin>
    );

export default App;