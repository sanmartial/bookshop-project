package com.globaroman.bookshopproject.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.springframework.util.ReflectionUtils;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field firstField = ReflectionUtils.findField(value.getClass(), firstFieldName);
            Field secondField = ReflectionUtils.findField(value.getClass(), secondFieldName);
            Object firstObj = getFieldValueByFieldName(firstField.getName(), value);
            Object secondObj = getFieldValueByFieldName(secondField.getName(), value);

            return firstObj == null && secondObj == null
                    || firstObj != null && firstObj.equals(secondObj);
        } catch (Exception e) {
            throw new RuntimeException("Passwords aren't equals", e);
        }
    }

    private Object getFieldValueByFieldName(String name, Object value) {
        try {
            String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = value.getClass().getMethod(methodName);
            return method.invoke(value);
        } catch (Exception e) {
            throw new RuntimeException("Can't get access to field" + name, e);

        }
    }
}
