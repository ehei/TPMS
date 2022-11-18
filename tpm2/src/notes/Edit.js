import { Edit, SimpleForm, TextInput } from 'react-admin';

export const NoteEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="text" />
        </SimpleForm>
    </Edit>
);