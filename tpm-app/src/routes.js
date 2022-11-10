import React from 'react';
import { Route } from 'react-router-dom';
import {TermList} from "./TermList";


export default [
    <Route exact path="/api/terms" component={TermList} />];