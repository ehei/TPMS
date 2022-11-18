import { Datagrid, List, TextField, EmailField } from 'react-admin';

export const InstructorList = () => (
    <List>
        <Datagrid rowClick="show">
            <TextField source="name" />
            <TextField source="phoneNumber" />
            <EmailField source="emailAddress" />
        </Datagrid>
    </List>
);