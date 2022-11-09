import { BooleanField, Datagrid, List, TextField } from 'react-admin';

export const TermList = () => (
    <List hasCreate={true}>
        <Datagrid rowClick="edit">
            <TextField source="title" />
            <TextField source="startDate" />
            <TextField source="endDate" />
        </Datagrid>
    </List>
);