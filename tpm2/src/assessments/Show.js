import {BooleanField, DateField, Show, SimpleShowLayout, TextField} from 'react-admin';

export const AssessmentShow = () => (
    <Show>
        <SimpleShowLayout>
            <TextField source="title" />
            <DateField source="startDate" />
            <DateField source="endDate" />
            <BooleanField source="performance" />
        </SimpleShowLayout>
    </Show>
);