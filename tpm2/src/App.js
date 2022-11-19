import './App.css';

import * as React from "react";
import {Admin, Resource, fetchUtils} from 'react-admin';
import {ListGuesser, ShowGuesser} from 'react-admin';
import jsonServerProvider from "ra-data-json-server";
import {TermList} from "./terms/List";
import {TermShow} from "./terms/Show";
import {TermEdit} from "./terms/Edit";
import {TermCreate} from "./terms/Create";
import {InstructorList} from "./instructors/List";
import {InstructorShow} from "./instructors/Show";
import {InstructorEdit} from "./instructors/Edit";
import {InstructorCreate} from "./instructors/Create";
import {AssessmentList} from "./assessments/List";
import {AssessmentShow} from "./assessments/Show";
import {AssessmentEdit} from "./assessments/Edit";
import {AssessmentCreate} from "./assessments/Create";
import {CourseEdit} from "./courses/Edit";
import {CourseCreate} from "./courses/Create";
import {CourseList} from "./courses/List";
import {CourseShow} from "./courses/Show";
import {NoteShow} from "./notes/Show";
import {NoteEdit} from "./notes/Edit";
import {NoteCreate} from "./notes/Create";
import authProvider from "./authentication/authProvider";
import {Dashboard} from "./dashboard/Dashboard";
import {FulltermList} from "./dashboard/FulltermList";
import {FulltermShow} from "./dashboard/FulltermShow";

const fetchJson = (url, options = {}) => {
    let token = localStorage.getItem('token');
    console.log("token to use for fetch = " + token);
    options.user = {
        authenticated: true,
        token: token
    }
    return fetchUtils.fetchJson(url, options);
};

const dataProvider = jsonServerProvider('http://localhost:8080/api', fetchJson);

const App = () => (
    <Admin dataProvider={dataProvider} authProvider={authProvider} dashboard={Dashboard}>
        <Resource name="terms" list={TermList} show={TermShow} edit={TermEdit} create={TermCreate}
                  recordRepresentation="title"/>
        <Resource name="instructors" list={InstructorList} show={InstructorShow} edit={InstructorEdit}
                  create={InstructorCreate} recordRepresentation="title"/>
        <Resource name="assessments" list={AssessmentList} show={AssessmentShow} edit={AssessmentEdit}
                  create={AssessmentCreate} recordRepresentation="title"/>
        <Resource name="courses" list={CourseList} show={CourseShow} edit={CourseEdit} create={CourseCreate}
                  recordRepresentation="title"/>
        <Resource name="notes" recordRepresentation="text" show={NoteShow} edit={NoteEdit} create={NoteCreate}/>
    </Admin>
);

export default App;