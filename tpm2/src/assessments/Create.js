import {BooleanInput, Create, DateInput, SimpleForm, TextInput} from 'react-admin';
import {validateDate, validateName} from "../validation";

export const AssessmentCreate = () => (
    <Create redirect={"list"}>
        <SimpleForm>
            <TextInput source="title" validate={validateName} />
            <DateInput source="startDate" validate={validateDate} />
            <DateInput source="endDate" validate={validateDate} />
            <BooleanInput source="performance" />
        </SimpleForm>
    </Create>
);