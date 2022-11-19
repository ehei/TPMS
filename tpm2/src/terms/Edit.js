import {DateInput, Edit, ReferenceArrayInput, SelectArrayInput, SimpleForm, TextInput} from 'react-admin';

export const TermEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="title" />
            <DateInput source="startDate" />
            <DateInput source="endDate" />
            <ReferenceArrayInput reference="courses" source="course_ids">
                <SelectArrayInput optionText={"title"} />
            </ReferenceArrayInput>
        </SimpleForm>
    </Edit>
);