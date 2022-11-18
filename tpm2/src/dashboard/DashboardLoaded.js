import React from 'react';

import { Card, CardContent, CardHeader, Box } from '@mui/material';
import Paper from '@mui/material/Paper';
import Stack from '@mui/material/Stack';
import { styled } from '@mui/material/styles';
import Typography from '@material-ui/core/Typography';
import { withStyles } from '@material-ui/core/styles';

const styles = {
    root: {
        width: 600,
        margin: 'auto',
        backgroundColor: '#cfcfcf',
    }
};

const DashboardLoadedView = ({
                                 items = [],
                                 total,
                                 handleLoadMore,
                                 classes
                             }) => {
        if (items.length === 0) {
            return (
                <Typography variant="h2" color="error" gutterBottom>
                    ERROR: List should not be empty!
                </Typography>);
        }

        return (
        <div className={classes.root}>
            <Stack
                direction="column"
                justifyContent="center"
                alignItems="center"
                spacing={2}
                width={"100%"}
            >
                {
                    items.map(term => (
                    <Card sx={{ minWidth: 275, width: 500 }} key={term.id}>
                        <CardContent>
                            <Typography variant="h2" color="secondary" gutterBottom>
                                { term.title }
                            </Typography>
                            <Typography variant="h5" component="div">
                                Start Date: { term.startDate }
                                <br />
                                End Date: { term.endDate }
                            </Typography>
                        </CardContent>
                    </Card>
                ))
            }
            <div></div>

            </Stack>
        </div>
    );
};


const DashboardLoaded = withStyles(styles)(DashboardLoadedView);

export default DashboardLoaded;