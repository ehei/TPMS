import {ArrayInput, DateInput, Edit, SelectInput, SimpleForm, SimpleFormIterator, TextInput} from 'react-admin';

export const CourseEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="title" />
            <SelectInput source="status" choices={[
                { id: "PlanToTake", name: "Plan to Take"},
                { id: "InProgress", name: "In Progress"},
                { id: "Completed", name: "Completed"},
                { id: "Dropped", name: "Dropped"}
            ]} />
            <DateInput source="startDate" />
            <DateInput source="endDate" />
            <ArrayInput source="notes">
                <SimpleFormIterator inline>
                    <TextInput source="text" />
                </SimpleFormIterator>
            </ArrayInput>
        </SimpleForm>
    </Edit>
);