import React, {Component} from 'react';
import { useState, useEffect } from 'react';
import { useDataProvider } from 'react-admin';
import { Title, useGetList } from 'react-admin';

import { Card, CardContent, CardHeader, Grid } from '@mui/material';

import DashboardLoading from './DashboardLoading'
import DashboardLoaded from './DashboardLoaded'
import DashboardEmpty from './DashboardEmpty'

export const Dashboard = () => {

    const { data, total, isLoading, error } = useGetList(
        'terms',
        {
            pagination: { page: 1, perPage: 5},
            sort: { field: 'id', order: 'ASC'}
        });

    if (isLoading) return <DashboardLoading />;
    if (error) return <DashboardEmpty />;

    if (total === 0) return <DashboardEmpty />;

    console.log(data[0]);

    return (<DashboardLoaded items={data}
                            total={total}
                            handleLoadMore={ () => {} }
    />);
};
