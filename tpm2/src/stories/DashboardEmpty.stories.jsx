import React from 'react';
import DashboardEmpty from '../dashboard/DashboardEmpty';
import { storiesOf } from '@storybook/react';

storiesOf('DashboardEmpty', module)
    .add('start', () => <DashboardEmpty />)
;