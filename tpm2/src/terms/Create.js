import {Create, DateInput, ReferenceArrayInput, SelectArrayInput, SimpleForm, TextInput} from 'react-admin';
import {validateDate, validateName} from "../validation";

export const TermCreate = () => (
    <Create redirect={"list"}>
        <SimpleForm>
            <TextInput source="title" validate={validateName} />
            <DateInput source="startDate" validate={validateDate} />
            <DateInput source="endDate" validate={validateDate} />
            <ReferenceArrayInput reference="courses" source="course_ids">
                <SelectArrayInput optionText={"title"} />
            </ReferenceArrayInput>
        </SimpleForm>
    </Create>
);