import {
    ArrayField,
    Datagrid,
    DateField,
    FunctionField,
    ReferenceArrayField,
    SelectField,
    Show,
    SimpleList,
    SimpleShowLayout,
    TextField
} from 'react-admin';

export const CourseShow = (props) => {

    return (
        <Show {...props}  >
            <SimpleShowLayout>
                <TextField source="title"/>
                <SelectField source="status" choices={[
                    {id: "PlanToTake", name: "Plan to Take"},
                    {id: "InProgress", name: "In Progress"},
                    {id: "Completed", name: "Completed"},
                    {id: "Dropped", name: "Dropped"}
                ]}/>
                <DateField source="startDate"/>
                <DateField source="endDate"/>
                <ReferenceArrayField label="Assessments" reference="assessments" source="assessment_ids"
                                     sort={{field: 'id', order: 'ASC'}}>
                    <SimpleList
                        primaryText={record => record.title}
                        linkType={"edit"}
                    />
                </ReferenceArrayField>
                <ReferenceArrayField label="Instructors" reference="instructors" source="instructor_ids"
                                     sort={{field: 'id', order: 'ASC'}}>
                    <SimpleList
                        primaryText={record => record.name}
                        linkType={"edit"}
                    />
                </ReferenceArrayField>
                <ArrayField source="notes">
                    <Datagrid>
                        <FunctionField label="Text" render={ record => `${record}` } />
                    </Datagrid>
                </ArrayField>
            </SimpleShowLayout>
        </Show>
    );
};