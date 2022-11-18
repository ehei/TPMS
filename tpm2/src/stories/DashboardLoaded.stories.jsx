import React from 'react';
import DashboardLoaded from '../dashboard/DashboardLoaded';
import { storiesOf } from '@storybook/react';

import { testdata } from './testdata.js';

storiesOf('DashboardLoaded', module)
    .add('start', () => <DashboardLoaded items={testdata} />)
;