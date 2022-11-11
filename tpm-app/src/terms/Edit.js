import { Edit, SimpleForm, TextInput, DateInput } from 'react-admin';

export const TermEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="title" />
            <DateInput source="startDate" />
            <DateInput source="endDate" />
        </SimpleForm>
    </Edit>
);