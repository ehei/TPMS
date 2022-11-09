import * as React from "react";
import { Admin, Resource, CreateButton, ListGuesser, ShowGuesser, EditGuesser } from 'react-admin';
import {springDataProvider, springHttpClient} from 'ra-data-spring-rest';
import httpClient from "./FetchClient";
import {TermList} from "./TermList";
import {TermShow} from "./TermShow";
import {TermEdit} from "./TermEdit";

const dataProvider = springDataProvider('http://localhost:8080/api', httpClient);

const App = () => (
    <Admin dataProvider={dataProvider}  >
        <Resource name="terms" list={TermList} show={TermShow} edit={TermEdit} />
    </Admin>
    );

export default App;