import { Datagrid, List, TextField, EmailField } from 'react-admin';

export const InstructorList = () => (
    <List hasCreate={true}>
        <Datagrid rowClick="edit">
            <TextField source="name" />
            <TextField source="phoneNumber" />
            <EmailField source="emailAddress" />
        </Datagrid>
    </List>
);