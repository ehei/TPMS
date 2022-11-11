import {Edit, EmailField, SimpleForm, TextInput} from 'react-admin';

export const InstructorEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="name" />
            <TextInput source="phoneNumber" />
            <EmailField source="emailAddress" />
        </SimpleForm>
    </Edit>
);