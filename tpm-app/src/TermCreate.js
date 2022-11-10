import { Create, SimpleForm, TextInput, DateInput } from 'react-admin';

export const TermCreate = () => (
    <Create redirect={"list"}>
        <SimpleForm>
            <TextInput source="title" />
            <DateInput source="startDate" />
            <DateInput source="endDate" />
        </SimpleForm>
    </Create>
);