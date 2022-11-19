import React from 'react';
import {useGetList, useGetMany} from 'react-admin';

import DashboardLoading from './DashboardLoading'
import DashboardLoaded from './DashboardLoaded'
import DashboardEmpty from './DashboardEmpty'

export const Dashboard = () => {

    const { data, total, isLoading, error } = useGetList(
        'fullTerms',
        {
            pagination: { page: 1, perPage: 5},
            sort: { field: 'id', order: 'ASC'}
        });

    if (isLoading) return <DashboardLoading />;
    if (error) return <DashboardEmpty />;

    if (total === 0) return <DashboardEmpty />;

    return (<DashboardLoaded items={data}
                            total={total}
                            handleLoadMore={ () => {}}
    />);
};
