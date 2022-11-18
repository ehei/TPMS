import {
    Create,
    SimpleForm,
    TextInput,
    SelectInput,
    DateInput,
    ArrayInput,
    SimpleFormIterator,
    AutocompleteInput,
    ReferenceArrayInput,
    ReferenceManyField,
    Datagrid,
    TextField,
    ReferenceInput,
    SelectArrayInput
} from 'react-admin';

export const CourseCreate = () => (
    <Create redirect={"list"}>
        <SimpleForm>
            <TextInput source="title"  name="title"/>
            <SelectInput source="status" choices={[
                { id: "PlanToTake", name: "Plan to Take"},
                { id: "InProgress", name: "In Progress"},
                { id: "Completed", name: "Completed"},
                { id: "Dropped", name: "Dropped"}
            ]} />
            <DateInput source="startDate"/>
            <DateInput source="endDate"/>
            <ReferenceArrayInput reference="assessments" source="assessment_ids">
                <SelectArrayInput optionText={"title"}/>
            </ReferenceArrayInput>
            <ReferenceArrayInput reference="instructors" source="instructor_ids">
                <SelectArrayInput optionText={"name"}/>
            </ReferenceArrayInput>
            <ArrayInput source="notes">
                <SimpleFormIterator inline>
                    <TextInput source="" />
                </SimpleFormIterator>
            </ArrayInput>
        </SimpleForm>
    </Create>
);