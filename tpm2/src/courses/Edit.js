import {
    ArrayInput,
    AutocompleteInput,
    DateInput,
    Edit,
    SelectInput,
    SimpleForm,
    SimpleFormIterator,
    TextInput,
    ReferenceArrayInput,
    SelectArrayInput
} from 'react-admin';

export const CourseEdit = () => {
    return (
        <Edit>
            <SimpleForm>
                <TextInput source="title"/>
                <SelectInput source="status" choices={[
                    {id: "PlanToTake", name: "Plan to Take"},
                    {id: "InProgress", name: "In Progress"},
                    {id: "Completed", name: "Completed"},
                    {id: "Dropped", name: "Dropped"}
                ]}/>
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
        </Edit>
    );
};