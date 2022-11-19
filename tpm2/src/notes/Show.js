import {Show, SimpleShowLayout, TextField} from 'react-admin';

export const NoteShow = () => (
    <Show>
        <SimpleShowLayout>
            <TextField source="text" />
        </SimpleShowLayout>
    </Show>
);