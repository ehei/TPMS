import {DateField, ReferenceArrayField, Show, SimpleList, SimpleShowLayout, TextField} from 'react-admin';

export const TermShow = () => (
    <Show>
        <SimpleShowLayout>
            <TextField source="title"/>
            <DateField source="startDate"/>
            <DateField source="endDate"/>
            <ReferenceArrayField label="Courses" reference="courses" source="course_ids"
                                 sort={{field: 'id', order: 'ASC'}}>
                <SimpleList
                    primaryText={record => record.title}
                    linkType={"edit"}
                />
            </ReferenceArrayField>
        </SimpleShowLayout>
    </Show>
);