import { Datagrid, List, TextField, DateField, BooleanField } from 'react-admin';

export const AssessmentList = () => (
    <List>
        <Datagrid rowClick="edit">
            <TextField source="title" />
            <DateField source="startDate" />
            <DateField source="endDate" />
            <BooleanField source="performance" />
        </Datagrid>
    </List>
);