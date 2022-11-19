import {Edit, SimpleForm, TextInput} from 'react-admin';
import {validateEmail, validateName, validatePhoneNumber} from "../validation";

export const InstructorEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="name" validate={validateName} />
            <TextInput source={"phoneNumber"} validate={validatePhoneNumber} />
            <TextInput source={"emailAddress"} validate={validateEmail} />
        </SimpleForm>
    </Edit>
);