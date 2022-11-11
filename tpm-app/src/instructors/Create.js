import {Create, EmailField, SimpleForm, TextInput} from 'react-admin';

export const InstructorCreate = () => (
    <Create>
        <SimpleForm>
            <TextInput source="name" />
            <TextInput source="phoneNumber" />
            <EmailField source="emailAddress" />
        </SimpleForm>
    </Create>
);