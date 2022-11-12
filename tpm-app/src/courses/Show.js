import {
    ArrayField,
    DateField,
    SelectField,
    Show,
    SimpleShowLayout,
    TextField,
    SimpleList,
    ReferenceManyField
} from 'react-admin';

export const CourseShow = () => (
    <Show>
        <SimpleShowLayout>
            <TextField source="title" />
            <SelectField source="status" choices={[
                { id: "PlanToTake", name: "Plan to Take"},
                { id: "InProgress", name: "In Progress"},
                { id: "Completed", name: "Completed"},
                { id: "Dropped", name: "Dropped"}
            ]} />
            <DateField source="startDate" />
            <DateField source="endDate" />
            <ReferenceManyField label="Notes"
                target="courses"
                reference="notes">
                <SimpleList primaryText="text" />
            </ReferenceManyField>
            <ArrayField source="notes">
                <SimpleList primaryText={record => record.text}/>
            </ArrayField>
        </SimpleShowLayout>
    </Show>
);