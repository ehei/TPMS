import {BooleanInput, Create, DateInput, SimpleForm, TextInput} from 'react-admin';

export const AssessmentCreate = () => (
    <Create redirect={"list"}>
        <SimpleForm>
            <TextInput source="title" />
            <DateInput source="startDate" />
            <DateInput source="endDate" />
            <BooleanInput source="performance" />
        </SimpleForm>
    </Create>
);