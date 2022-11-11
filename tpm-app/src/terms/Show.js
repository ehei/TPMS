import {DateField, Show, SimpleShowLayout, ReferenceManyField, TextField} from 'react-admin';

export const TermShow = () => (
    <Show>
        <SimpleShowLayout>
            <TextField source="title" />
            <DateField source="startDate" />
            <DateField source="endDate" />
        </SimpleShowLayout>
    </Show>
);