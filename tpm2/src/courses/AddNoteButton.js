import React from 'react';
import {Link} from 'react-router-dom';
import ChatBubbleIcon from '@material-ui/icons/ChatBubble';
import {withStyles} from '@material-ui/core/styles';
import {Button, useRecordContext} from 'react-admin';

const styles = {
    button: {
        marginTop: '1em'
    }
};

const AddNoteButton = ({classes, props}) => {
    const record = useRecordContext(props);
    console.log("AddNoteButton");
    console.log(record);
    if (! record) return null;
    console.log(record);
    return <Button
            className={classes.button}
            variant="raised"
            component={Link}
            label="Add a comment"
            title="Add a comment"
            to={{
                pathname: "../notes/create",
                state: { record: { post_id: record.id } },
            }}        >
            <ChatBubbleIcon/>
        </Button>;
};

export default withStyles(styles)(AddNoteButton);