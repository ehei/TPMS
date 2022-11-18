import {DateInput, Edit, SimpleForm, TextInput, BooleanInput} from 'react-admin';

export const AssessmentEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="title" />
            <DateInput source="startDate" />
            <DateInput source="endDate" />
            <BooleanInput source="performance" />
        </SimpleForm>
    </Edit>
);