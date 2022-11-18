import React from 'react';

import { Card, CardContent, CardHeader, Grid } from '@mui/material';
import { withStyles } from '@material-ui/core/styles';

const PlaceHolder = withStyles({
    root: {
        width: 400,
        backgroundColor: '#cfcfcf',
        display: 'block',
    },
})(({ classes }) => <span className={classes.root}>&nbsp;</span>);

const styles = {
    root: {
        width: 600,
        margin: 'auto',
    },
    card: {
        marginTop: '0.7em',
        marginBottom: '1em',
    },
};

const DashboardLoadingView = ({classes}) => (
    <div className={classes.root}>
        <Card sx={{ minWidth: 275 }}>
            <CardContent>
                    <PlaceHolder />
                    <PlaceHolder />
                    <PlaceHolder />
            </CardContent>
        </Card>
        <Card sx={{ minWidth: 275 }}>
            <CardContent>
                    <PlaceHolder />
                    <PlaceHolder />
                    <PlaceHolder />
            </CardContent>
        </Card>
    </div>
);

const DashboardLoading = withStyles(styles)(DashboardLoadingView);

export default DashboardLoading;