import {
    Edit,
    SimpleForm,
    TextInput,
    DateInput,
    ReferenceInput,
    AutocompleteInput,
    ReferenceArrayInput, SelectArrayInput
} from 'react-admin';
import {NoteCreate} from "../notes/Create";
import {CourseCreate} from "../courses/Create";

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