import * as React from "react";
import { Admin, Resource, CreateButton, ListGuesser, ShowGuesser, EditGuesser } from 'react-admin';
import {springDataProvider, springHttpClient} from 'ra-data-spring-rest';
import httpClient from "./FetchClient";
import {TermList} from "./TermList";
import {TermShow} from "./TermShow";
import {TermEdit} from "./TermEdit";
import {TermCreate} from "./TermCreate";
import {InstructorList} from "./InstructorList";
import {InstructorEdit} from "./InstructorEdit";
import {InstructorShow} from "./InstructorShow";
import {InstructorCreate} from "./InstructorCreate";

const dataProvider = httpClient('http://localhost:8080');

const App = () => (
    <Admin dataProvider={dataProvider}  >
        <Resource name="terms" list={TermList} show={TermShow} edit={TermEdit} create={TermCreate} />
        <Resource name="instructors" list={InstructorList} show={InstructorShow} edit={InstructorEdit} create={InstructorCreate} />
    </Admin>
    );

export default App;