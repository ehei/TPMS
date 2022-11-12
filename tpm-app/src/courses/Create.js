import {Create, SimpleForm, TextInput, SelectInput, DateInput, ArrayInput, SimpleFormIterator} from 'react-admin';

export const CourseCreate = () => (
    <Create>
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
    </Create>
);