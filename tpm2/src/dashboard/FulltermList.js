import { ArrayField, ChipField, Datagrid, DateField, List, NumberField, SingleFieldList, TextField } from 'react-admin';

export const FulltermList = () => (
    <List>
        <Datagrid rowClick="show">
            <TextField source="id" />
            <TextField source="title" />
            <DateField source="startDate" />
            <DateField source="endDate" />
            <ArrayField source="courses"><SingleFieldList><ChipField source="id" /></SingleFieldList></ArrayField>
            <NumberField source="user.id" />
        </Datagrid>
    </List>
);