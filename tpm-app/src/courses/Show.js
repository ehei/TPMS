import {
    ArrayField,
    DateField,
    SelectField,
    Show,
    SimpleShowLayout,
    TextField,
    SimpleList
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
            <ArrayField source="notes">
                <SimpleList primaryText={record => record.text}/>
            </ArrayField>
        </SimpleShowLayout>
    </Show>
);