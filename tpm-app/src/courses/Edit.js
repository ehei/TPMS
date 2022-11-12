import {
    ArrayInput,
    AutocompleteInput,
    DateInput,
    Edit,
    ReferenceInput,
    SelectInput,
    SimpleForm,
    SimpleFormIterator,
    TextInput,
    ReferenceArrayInput
} from 'react-admin';

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
            <ReferenceArrayInput
                source="notes_id"
                reference="notes">
                <AutocompleteInput label="Note" />
            </ReferenceArrayInput>
        </SimpleForm>
    </Edit>
);