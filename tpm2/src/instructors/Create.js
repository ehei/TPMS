import {Create, SimpleForm, TextInput} from 'react-admin';
import {validateEmail, validateName, validatePhoneNumber} from "../validation";

export const InstructorCreate = () => (
    <Create redirect={"list"}>
        <SimpleForm>
            <TextInput source="name" validate={validateName} />
            <TextInput source={"phoneNumber"} validate={validatePhoneNumber} />
            <TextInput source={"emailAddress"} validate={validateEmail} />
        </SimpleForm>
    </Create>
);