import {Create, DateInput, ReferenceArrayInput, SelectArrayInput, SimpleForm, TextInput} from 'react-admin';
import {
    required,
    minLength,
    maxLength,
    minValue,
    maxValue,
    number,
    regex,
    email,
    choices
} from 'react-admin';

const validateName = [required(), minLength(2), maxLength(15)];
const validateEmail = email();

export const TermCreate = () => (
    <Create redirect={"list"}>
        <SimpleForm>
            <TextInput source="title"/>
            <DateInput source="startDate"/>
            <DateInput source="endDate"/>
            <ReferenceArrayInput reference="courses" source="course_ids">
                <SelectArrayInput optionText={"title"} />
            </ReferenceArrayInput>
        </SimpleForm>
    </Create>
);