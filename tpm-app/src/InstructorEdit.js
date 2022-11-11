import { Edit, SimpleForm, TextInput } from 'react-admin';

export const InstructorEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="name" />
            <TextInput source="phoneNumber" />
            <TextInput source="emailAddress" />
        </SimpleForm>
    </Edit>
);