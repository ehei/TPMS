import {
    Create,
    SimpleForm,
    TextInput,
    SelectInput,
    DateInput,
    ArrayInput,
    SimpleFormIterator,
    AutocompleteInput,
    ReferenceArrayInput,
    ReferenceManyField,
    Datagrid,
    TextField, ReferenceInput
} from 'react-admin';

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
                <SimpleFormIterator>
                    <TextInput source="text" />
                </SimpleFormIterator>
            </ArrayInput>
            <ReferenceInput
                label="Term"
                source="term_id"
                reference="terms">
                <SelectInput optionText="title" />
            </ReferenceInput>
        </SimpleForm>
    </Create>
);