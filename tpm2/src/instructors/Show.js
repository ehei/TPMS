import {Show, SimpleShowLayout, TextField} from 'react-admin';

export const InstructorShow = () => (
    <Show>
        <SimpleShowLayout>
            <TextField source="name" />
            <TextField source="phoneNumber" />
            <TextField source="emailAddress" />
        </SimpleShowLayout>
    </Show>
);