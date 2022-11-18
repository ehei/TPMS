import {
    Create,
    SimpleForm,
    TextInput,
    DateInput,
    ReferenceArrayInput,
    ArrayInput,
    AutocompleteInput,
    SimpleFormIterator, SelectArrayInput
} from 'react-admin';

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