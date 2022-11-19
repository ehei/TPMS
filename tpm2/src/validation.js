import {
    required,
    minLength,
    maxLength,
    email,
    regex
} from 'react-admin';

export const validateName = [required(), minLength(2), maxLength(55)];

export const checkDate = (value, values) => {
    let error = undefined;

    if (values.startDate && values.endDate) {
        const start = new Date(values.startDate);
        const end = new Date(values.endDate);

        const before = end < start;

        if (before) {
            error = 'End date must be after Start date';
        }
    }

    return error;
}
export const validateDate = [required(), checkDate];

export const validateEmail = [required(), email()];

export const validatePhoneNumber = [regex(/^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/, 'Phone number must be in the format of 111-222-3333; country code is optional.')];

export const validateNote = [minLength(2), maxLength(100)]