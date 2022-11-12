import { ReferenceArrayField, Datagrid, List, TextField, SingleFieldList, ChipField } from 'react-admin';

export const TermList = () => (
    <List>
        <Datagrid rowClick="show">
            <TextField source="title" />
            <TextField source="startDate" />
            <TextField source="endDate" />
            <ReferenceArrayField label="Courses" reference="courses" source="id">
                <SingleFieldList>
                    <ChipField source="title" />
                </SingleFieldList>
            </ReferenceArrayField>
        </Datagrid>
    </List>
);