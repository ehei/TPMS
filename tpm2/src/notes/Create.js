import { Create, SimpleForm, TextInput } from 'react-admin';

export const NoteCreate = () => {

    return (
        <Create>
            <SimpleForm>
                <TextInput source="text"/>
            </SimpleForm>
        </Create>
    );
};