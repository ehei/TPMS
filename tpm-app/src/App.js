import * as React from "react";
import { Admin, Resource, CreateButton, ListGuesser, ShowGuesser, EditGuesser } from 'react-admin';
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

const dataProvider = httpClient('http://localhost:8080');

const App = () => (
    <Admin dataProvider={dataProvider}  >
        <Resource name="terms" list={TermList} show={TermShow} edit={TermEdit} create={TermCreate} />
        <Resource name="instructors" list={InstructorList} show={InstructorShow} edit={InstructorEdit} create={InstructorCreate} />
        <Resource name="assessments" list={AssessmentList} show ={AssessmentShow} edit={AssessmentEdit} create={AssessmentCreate} />
        <Resource name="courses" list={CourseList} show ={CourseShow} edit={CourseEdit} create={CourseCreate} />
    </Admin>
);

export default App;