import {useRecordContext} from 'react-admin';

export const NoteList = (...props) => {
    const record = useRecordContext(props);
    console.log("NoteList");
    console.log(record.notes);

    return ( null
    );
};