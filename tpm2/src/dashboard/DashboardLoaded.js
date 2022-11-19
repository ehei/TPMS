import React from 'react';

import {Card, CardContent} from '@mui/material';
import Stack from '@mui/material/Stack';
import Typography from '@material-ui/core/Typography';
import {withStyles} from '@material-ui/core/styles';

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
                marginTop={2}
                paddingTop={2}
                direction="column"
                justifyContent="center"
                alignItems="center"
                spacing={2}
                width={"100%"}
            >
                {
                    items.map(term => {

                        return (
                            <Card sx={{minWidth: 275, width: 500}} key={term.id}>
                                <CardContent>
                                    <Typography variant="h2" color="secondary" gutterBottom>
                                        <i>{term.title}</i>
                                    </Typography>
                                    <Typography variant="h5" component="div">
                                        Start: {term.startDate}  End: {term.endDate}
                                    </Typography>
                                    <Typography variant="h5" component="div">
                                        <br/><i>Courses</i><br/>
                                        { term.courses.map(course => {
                                            return (
                                                <Typography variant="h6" component="div" key={course.id}>
                                                    <br/>
                                                    {course.title}<br/>
                                                    Start: {course.startDate} End: {course.endDate}
                                                    <br/>&nbsp;&nbsp;&nbsp;<i>Assessments</i><br/>
                                                    { course.assessments.map(assessment => {
                                                        return (
                                                            <Typography variant="h6" component="div" key={assessment.id}>
                                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{assessment.title}  ({assessment.performance? "Performance" : "Objective"})<br/>
                                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Start: {assessment.startDate} End: {assessment.endDate}
                                                                <br/>
                                                            </Typography>);
                                                    })}
                                                    &nbsp;&nbsp;&nbsp;<i>Instructors</i><br/>
                                                    { course.instructors.map(instructor => {
                                                        return (
                                                            <Typography variant="h6" component="div" key={instructor.id}>
                                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{instructor.name}<br/>
                                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Phone: {instructor.phoneNumber} Email: {instructor.emailAddress}
                                                                <br/>
                                                            </Typography>);
                                                    })}
                                                </Typography>);
                                        })}
                                    </Typography>
                                </CardContent>
                            </Card>
                        );
                    })
                }
            <div></div>

            </Stack>
        </div>
    );
};


const DashboardLoaded = withStyles(styles)(DashboardLoadedView);

export default DashboardLoaded;