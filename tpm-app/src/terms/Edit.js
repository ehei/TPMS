import { Edit, SimpleForm, TextInput, DateInput, ReferenceInput, AutocompleteInput } from 'react-admin';

export const TermEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="title" />
            <DateInput source="startDate" />
            <DateInput source="endDate" />
            <ReferenceInput
                source="course_id"
                reference="courses">
                <AutocompleteInput label="Course" />
            </ReferenceInput>
        </SimpleForm>
    </Edit>
);