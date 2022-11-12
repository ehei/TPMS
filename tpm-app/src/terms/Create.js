import {
    Create,
    SimpleForm,
    TextInput,
    DateInput,
    ReferenceArrayInput,
    ArrayInput,
    AutocompleteInput,
    SimpleFormIterator
} from 'react-admin';

export const TermCreate = () => (
    <Create redirect={"list"}>
        <SimpleForm>
            <TextInput source="title"/>
            <DateInput source="startDate"/>
            <DateInput source="endDate"/>
            <ReferenceArrayInput source="term_id" reference="courses" >
                 <AutocompleteInput label="Course" />
            </ReferenceArrayInput>
        </SimpleForm>
    </Create>
);