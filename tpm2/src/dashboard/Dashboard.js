import React from 'react';
import {useGetList} from 'react-admin';

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
