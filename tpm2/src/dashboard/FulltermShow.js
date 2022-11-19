import {
    ArrayField,
    BooleanField,
    Datagrid,
    DateField,
    NumberField,
    Show,
    SimpleShowLayout,
    TextField
} from 'react-admin';

export const FulltermShow = () => (
    <Show>
        <SimpleShowLayout>
            <TextField source="id"/>
            <TextField source="title"/>
            <DateField source="startDate"/>
            <DateField source="endDate"/>
            <ArrayField source="courses">
                <Datagrid>
                    <TextField source="id"/>
                    <TextField source="title"/>
                    <TextField source="status"/>
                    <DateField source="startDate"/>
                    <DateField source="endDate"/>
                    <ArrayField source="instructors">
                    <Datagrid>
                        <TextField source="id"/>
                        <TextField source="name"/>
                        <TextField source="phoneNumber"/>
                        <TextField source="emailAddress"/>
                    </Datagrid>
                    </ArrayField>
                    <ArrayField source="assessments">
                    <Datagrid>
                        <TextField source="id"/>
                        <TextField source="title"/>
                        <DateField source="startDate"/>
                        <DateField source="endDate"/>
                        <BooleanField source="performance"/>
                    </Datagrid>
                </ArrayField>
                <TextField source="notes"/>
            </Datagrid>
            </ArrayField>
            <TextField source="user.username"/>
        </SimpleShowLayout>
    </Show>
);