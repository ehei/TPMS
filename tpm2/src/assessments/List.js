import {BooleanField, Datagrid, DateField, List, TextField} from 'react-admin';

export const AssessmentList = () => (
    <List>
        <Datagrid rowClick="show">
            <TextField source="title" />
            <DateField source="startDate" />
            <DateField source="endDate" />
            <BooleanField source="performance" />
        </Datagrid>
    </List>
);