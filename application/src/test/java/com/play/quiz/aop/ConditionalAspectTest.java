package com.play.quiz.aop;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import com.play.quiz.aop.annotation.Conditional;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

@ExtendWith(MockitoExtension.class)
class ConditionalAspectTest {

    @Mock
    private Environment environment;

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    private ConditionalAspect conditionalAspect;

    @BeforeEach
    public void init() {
        conditionalAspect = new ConditionalAspect(environment);
    }

    @Test
    void given_property_true_value_true_match_when_conditionalMethod_then_proceed() throws Throwable {
        final MethodSignature methodSignature = anonumousMethodSignature("trueMatchIfMissing");

        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(environment.getProperty("application.property")).thenReturn("true");
        when(proceedingJoinPoint.proceed()).thenReturn(new Object());

        Object result = conditionalAspect.conditionalMethod(proceedingJoinPoint);

        assertNotNull(result);
    }

    @Test
    void given_property_false_value_true_match_when_conditionalMethod_then_null() throws Throwable {
        final MethodSignature methodSignature = anonumousMethodSignature("trueMatchIfMissing");

        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(environment.getProperty("application.property")).thenReturn("false");

        Object result = conditionalAspect.conditionalMethod(proceedingJoinPoint);

        assertNull(result);
    }

    @Test
    void given_property_true_value_false_match_when_conditionalMethod_then_null() throws Throwable {
        final MethodSignature methodSignature = anonumousMethodSignature("falseMatchIfMissing");

        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(environment.getProperty("application.property")).thenReturn("true");

        Object result = conditionalAspect.conditionalMethod(proceedingJoinPoint);

        assertNull(result);
    }

    @Test
    void given_property_false_value_false_match_when_conditionalMethod_then_proceed() throws Throwable {
        final MethodSignature methodSignature = anonumousMethodSignature("falseMatchIfMissing");

        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(environment.getProperty("application.property")).thenReturn("false");
        when(proceedingJoinPoint.proceed()).thenReturn(new Object());

        Object result = conditionalAspect.conditionalMethod(proceedingJoinPoint);

        assertNotNull(result);
    }

    @Test
    void given_property_true_value_true_no_match_when_conditionalMethod_then_proceed() throws Throwable {
        final MethodSignature methodSignature = anonumousMethodSignature("trueMatchIfMissingFalse");

        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(environment.getProperty("application.property")).thenReturn("true");
        when(proceedingJoinPoint.proceed()).thenReturn(new Object());

        Object result = conditionalAspect.conditionalMethod(proceedingJoinPoint);

        assertNotNull(result);
    }

    @Test
    void given_property_false_value_true_no_match_when_conditionalMethod_then_null() throws Throwable {
        final MethodSignature methodSignature = anonumousMethodSignature("trueMatchIfMissingFalse");

        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(environment.getProperty("application.property")).thenReturn("false");

        Object result = conditionalAspect.conditionalMethod(proceedingJoinPoint);

        assertNull(result);
    }

    @Test
    void given_property_true_value_false_no_match_when_conditionalMethod_then_null() throws Throwable {
        final MethodSignature methodSignature = anonumousMethodSignature("trueMatchIfMissingFalse");

        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(environment.getProperty("application.property")).thenReturn("true");

        Object result = conditionalAspect.conditionalMethod(proceedingJoinPoint);

        assertNull(result);
    }

    @Test
    void given_property_false_value_false_no_match_when_conditionalMethod_then_proceed() throws Throwable {
        final MethodSignature methodSignature = anonumousMethodSignature("falseMatchIfMissingFalse");

        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(environment.getProperty("application.property")).thenReturn("false");
        when(proceedingJoinPoint.proceed()).thenReturn(new Object());

        Object result = conditionalAspect.conditionalMethod(proceedingJoinPoint);

        assertNotNull(result);
    }

    @Test
    void given_no_property_value_true_match_when_conditionalMethod_then_proceed() throws Throwable {
        final MethodSignature methodSignature = anonumousMethodSignature("trueMatchIfMissing");

        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(environment.getProperty("application.property")).thenReturn(null);
        when(proceedingJoinPoint.proceed()).thenReturn(new Object());

        Object result = conditionalAspect.conditionalMethod(proceedingJoinPoint);

        assertNotNull(result);
    }

    @Test
    void given_no_property_no_value_no_match_when_conditionalMethod_then_proceed() throws Throwable {
        final MethodSignature methodSignature = anonumousMethodSignature("trueMatchIfMissing");

        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(environment.getProperty("application.property")).thenReturn(null);
        when(proceedingJoinPoint.proceed()).thenReturn(new Object());

        Object result = conditionalAspect.conditionalMethod(proceedingJoinPoint);

        assertNotNull(result);
    }

    private MethodSignature anonumousMethodSignature(String methodName) {
        return new MethodSignature() {
            @Override
            public String toShortString() {
                return null;
            }

            @Override
            public String toLongString() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public int getModifiers() {
                return 0;
            }

            @Override
            public Class<Object> getDeclaringType() {
                return null;
            }

            @Override
            public String getDeclaringTypeName() {
                return null;
            }

            @Override
            public Class<?>[] getParameterTypes() {
                return new Class[0];
            }

            @Override
            public String[] getParameterNames() {
                return new String[0];
            }

            @Override
            public Class<?>[] getExceptionTypes() {
                return new Class[0];
            }

            @Override
            public Class<Object> getReturnType() {
                return null;
            }

            @SneakyThrows
            @Override
            public Method getMethod() {
                return TestClass.class.getDeclaredMethod(methodName);
            }
        };
    }

    private static class TestClass {
        @Conditional(property = "application.property", value = "true", matchIfMissing = true)
        private String trueMatchIfMissing() {
            return "trueMatchIfMissing.method()";
        }

        @Conditional(property = "application.property", value = "false", matchIfMissing = true)
        private String falseMatchIfMissing() {
            return "falseMatchIfMissing.method()";
        }

        @Conditional(property = "application.property", value = "true")
        private String trueMatchIfMissingFalse() {
            return "trueMatchIfMissingFalse.method()";
        }

        @Conditional(property = "application.property", value = "false")
        private String falseMatchIfMissingFalse() {
            return "trueMatchIfMissingFalse.method()";
        }

        @Conditional(property = "application.property")
        private String noValueMatchIfMissingFalse() {
            return "trueMatchIfMissingFalse.method()";
        }
    }
}
