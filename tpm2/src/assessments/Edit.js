import {BooleanInput, DateInput, Edit, SimpleForm, TextInput} from 'react-admin';
import {validateDate, validateName} from "../validation";

export const AssessmentEdit = () => {


    return (
        <Edit>
            <SimpleForm>
                <TextInput source="title" validate={validateName}/>
                <DateInput source="startDate" validate={validateDate}/>
                <DateInput source="endDate" validate={validateDate}/>
                <BooleanInput source="performance"/>
            </SimpleForm>
        </Edit>
    );
};