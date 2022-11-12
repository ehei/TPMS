import * as React from "react";
import {Admin, Resource, CreateButton, ListGuesser, ShowGuesser, EditGuesser, fetchUtils} from 'react-admin';
import httpClient from "./FetchClient";
import {InstructorList} from "./instructors/List";
import {InstructorEdit} from "./instructors/Edit";
import {InstructorShow} from "./instructors/Show";
import {InstructorCreate} from "./instructors/Create";
import {TermList} from "./terms/List";
import {TermShow} from "./terms/Show";
import {TermEdit} from "./terms/Edit";
import {TermCreate} from "./terms/Create";
import {AssessmentList} from "./assessments/List";
import {AssessmentShow} from "./assessments/Show";
import {AssessmentEdit} from "./assessments/Edit";
import {AssessmentCreate} from "./assessments/Create";
import {CourseList} from "./courses/List";
import {CourseShow} from "./courses/Show";
import {CourseEdit} from "./courses/Edit";
import {CourseCreate} from "./courses/Create";
//import {springDataProvider, springHttpClient} from "ra-data-spring-rest";
//import provider from "ra-data-simple-rest";
//import provider from "ra-data-json-hal";
//import springDataProvider from "./SpringRestFetch";
import provider from "./json_hal/index";

const dataProvider = provider('http://localhost:8080');
//const dataProvider = springDataProvider('http://localhost:8080');

const App = () => (
    <Admin dataProvider={dataProvider}  >
        <Resource name="terms" list={TermList} show={TermShow} edit={TermEdit} create={TermCreate} recordRepresentation="title" />
        <Resource name="instructors" list={InstructorList} show={InstructorShow} edit={InstructorEdit} create={InstructorCreate} recordRepresentation="name" />
        <Resource name="assessments" list={AssessmentList} show ={AssessmentShow} edit={AssessmentEdit} create={AssessmentCreate} recordRepresentation="title" />
        <Resource name="courses" list={CourseList} show ={CourseShow} edit={CourseEdit} create={CourseCreate} recordRepresentation="title" />
    </Admin>
);

export default App;