import {Datagrid, List, TextField} from 'react-admin';

export const TermList = () => (
    <List>
        <Datagrid rowClick="show">
            <TextField source="title" />
            <TextField source="startDate" />
            <TextField source="endDate" />
        </Datagrid>
    </List>
);