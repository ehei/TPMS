import React from 'react';

import {Card, CardContent} from '@mui/material';
import {withStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import InfoIcon from '@material-ui/icons/Info';

const styles = {
    root: {
        width: 600,
        margin: 'auto',
    },
    content: {
        display: 'flex',
    },
    icon: {
        width: 50,
        height: 50,
        paddingRight: '1em',
    },
    image: {
        width: 50,
        marginTop: '0.5em',
        display: 'block',
    },
};

const DashboardEmptyView = ({classes}) => (
    <Card className={classes.root}>
        <CardContent className={classes.content}>
            <InfoIcon color="primary" className={classes.icon} />
            <div>
                <Typography >
                    This page will display the Terms and related data created.
                    <br /><br />
                    Click on Terms to get started . . .
                </Typography>
            </div>
        </CardContent>
    </Card>
);

const DashboardEmpty = withStyles(styles)(DashboardEmptyView);

export default DashboardEmpty;