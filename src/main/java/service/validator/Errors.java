package service.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Errors {
    public Map<String, List<String>> errors;

    public Errors() {
        errors = new HashMap<>();
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public Map<String, List<String>> getAllErrors() {
        return errors;
    }

    public void setAllErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public List<String> getFieldErrors(String field) {
        return errors.get(field);
    }

    public void setFieldErrors(String field, List<String> messages) {
        if (messages != null && messages.size() != 0) {
            errors.put(field, messages);
        }
    }

    public void addFieldError(String field, String message) {
        if (message != null) {
            getFieldErrors(field).add(message);
        }
    }
}
