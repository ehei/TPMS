import { Create, SimpleForm, TextInput, DateInput, BooleanInput } from 'react-admin';

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