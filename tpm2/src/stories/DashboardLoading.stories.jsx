import React from 'react';
import DashboardLoading from '../dashboard/DashboardLoading';
import { storiesOf } from '@storybook/react';

storiesOf('DashboardLoading', module)
    .add('start', () => <DashboardLoading />);