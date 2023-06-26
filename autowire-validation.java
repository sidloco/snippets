public interface CustomValidation {
    // No methods needed, it acts as a marker interface
}

@Component
public class CustomValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomValidation.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // Custom validation logic here
    }
}

@Service
public class MyService {

    @Autowired
    private Validator customValidator;

    public void performValidation(Object objectToValidate) {
        // Manually call the validation
        BindingResult bindingResult = new BeanPropertyBindingResult(objectToValidate, "objectToValidate");
        customValidator.validate(objectToValidate, bindingResult);
        
        if (bindingResult.hasErrors()) {
            // Handle validation errors
        }
    }
}

@Service
public class MyService {

    @Autowired
    private Validator customValidator;

    public void performValidation(Object objectToValidate, Class<?>... groups) {
        // Manually call the validation with specific groups
        BindingResult bindingResult = new BeanPropertyBindingResult(objectToValidate, "objectToValidate");
        customValidator.validate(objectToValidate, bindingResult, groups);
        
        if (bindingResult.hasErrors()) {
            // Handle validation errors
        }
    }
}

public class FirstGroupValidator implements ConstraintValidator<FirstGroup, Object> {

    @Override
    public void initialize(FirstGroup constraintAnnotation) {
        // Initialization logic if needed
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // Custom validation logic for the first group
        return true; // Return true for demonstration purposes
    }
}


public class SecondGroupValidator implements ConstraintValidator<SecondGroup, Object> {

    @Override
    public void initialize(SecondGroup constraintAnnotation) {
        // Initialization logic if needed
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // Custom validation logic for the second group
        return true; // Return true for demonstration purposes
    }
}


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FirstGroupValidator.class)
public @interface FirstGroup {
    String message() default "First group validation failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SecondGroupValidator.class)
public @interface SecondGroup {
    String message() default "Second group validation failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

public class MyModel implements CustomValidation {
    
    @NotNull(message = "Name cannot be null", groups = FirstGroup.class)
    private String name;
    
    @Size(min = 5, max = 10, message = "Description must be between 5 and 10 characters", groups = SecondGroup.class)
    private String description;

    // Getters and setters
}
