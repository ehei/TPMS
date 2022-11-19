import {
    ArrayInput,
    DateInput,
    Edit,
    ReferenceArrayInput, required,
    SelectArrayInput,
    SelectInput,
    SimpleForm,
    SimpleFormIterator,
    TextInput
} from 'react-admin';
import {validateDate, validateName, validateNote} from "../validation";

export const CourseEdit = () => {
    return (
        <Edit>
            <SimpleForm>
                <TextInput source="title" validate={validateName}/>
                <SelectInput source="status" choices={[
                    {id: "PlanToTake", name: "Plan to Take"},
                    {id: "InProgress", name: "In Progress"},
                    {id: "Completed", name: "Completed"},
                    {id: "Dropped", name: "Dropped"}
                ]} validation={required()} />
                <DateInput source="startDate" validate={validateDate}/>
                <DateInput source="endDate" validate={validateDate}/>
                <ReferenceArrayInput reference="assessments" source="assessment_ids">
                    <SelectArrayInput optionText={"title"}/>
                </ReferenceArrayInput>
                <ReferenceArrayInput reference="instructors" source="instructor_ids">
                    <SelectArrayInput optionText={"name"}/>
                </ReferenceArrayInput>
                <ArrayInput source="notes">
                    <SimpleFormIterator inline>
                        <TextInput source="" validate={validateNote}  />
                    </SimpleFormIterator>
                </ArrayInput>
            </SimpleForm>
        </Edit>
    );
};