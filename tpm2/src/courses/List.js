import {Datagrid, DateField, List, SelectField, TextField} from 'react-admin';

export const CourseList = () => (
    <List>
        <Datagrid rowClick="show">
            <TextField source="title" />
            <SelectField source="status" choices={[
                { id: "PlanToTake", name: "Plan to Take"},
                { id: "InProgress", name: "In Progress"},
                { id: "Completed", name: "Completed"},
                { id: "Dropped", name: "Dropped"}
            ]} />
            <DateField source="startDate" />
            <DateField source="endDate" />
        </Datagrid>
    </List>
);