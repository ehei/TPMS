import { Datagrid, List, TextField } from 'react-admin';

export const InstructorList = () => (
    <List hasCreate={true}>
        <Datagrid rowClick="edit">
            <TextField source="name" />
            <TextField source="phoneNumber" />
            <TextField source="emailAddress" />
        </Datagrid>
    </List>
);