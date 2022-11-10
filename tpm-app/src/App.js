import * as React from "react";
import { Admin, Resource, CreateButton, ListGuesser, ShowGuesser, EditGuesser } from 'react-admin';
import {springDataProvider, springHttpClient} from 'ra-data-spring-rest';
import httpClient from "./FetchClient";
import {TermList} from "./TermList";
import {TermShow} from "./TermShow";
import {TermEdit} from "./TermEdit";
import {TermCreate} from "./TermCreate";

const dataProvider = httpClient('http://localhost:8080');

const App = () => (
    <Admin dataProvider={dataProvider}  >
        <Resource name="terms" list={TermList} show={TermShow} edit={TermEdit} create={TermCreate} />
    </Admin>
    );

export default App;