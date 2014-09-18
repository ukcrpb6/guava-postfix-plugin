package uk.co.drache.intellij.codeinsight.postfix.templates.utils;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Static utility class pertaining to stubbing of JVM classes.
 *
 * @author Bob Browning
 */
public class JvmCodeStubs {

  /**
   * Prevent inheritance and instantiation.
   */
  private JvmCodeStubs() {
  }

  /**
   * Create stub class signature.
   *
   * @param classToStub The class to stub
   */
  private static String getStubClassSignature(Class<?> classToStub) {
    if (classToStub.getTypeParameters().length > 0) {
      return classToStub.getName() + "<" + Joiner.on(", ").join(classToStub.getTypeParameters()) + ">";
    }
    return classToStub.getName();
  }

  /**
   * Create simple stub class signature.
   *
   * @param classToStub The class to stub
   */
  private static String getSimpleStubClassSignature(Class<?> classToStub) {
    if (classToStub.getTypeParameters().length > 0) {
      return classToStub.getSimpleName() + "<" + Joiner.on(", ").join(classToStub.getTypeParameters()) + ">";
    }
    return classToStub.getSimpleName();
  }

  /**
   * Create stub top-level class.
   *
   * @param classToStub The class to stub
   */
  public static String getStubForTopLevelClass(Class<?> classToStub) {
    return "package " + classToStub.getPackage().getName() + ";\n" + getStubForClass(classToStub);
  }

  /**
   * Create stub class without package.
   *
   * @param classToStub The class to stub
   */
  private static String getStubForClass(Class<?> classToStub) {
    StringBuilder sb = new StringBuilder();
    // Stub the class signature
    sb.append(getModifiersSignature(classToStub.getModifiers()))
        .append("class ")
        .append(getSimpleStubClassSignature(classToStub));

    if (classToStub.getSuperclass() != null) {
      sb.append(" extends ").append(getStubClassSignature(classToStub.getSuperclass()));
    }

    Class<?>[] interfaces = classToStub.getInterfaces();
    if (interfaces.length > 0) {
      sb.append(" implements ");
      sb.append(Joiner.on(", ").join(
          Iterables.transform(ImmutableList.copyOf(interfaces),
                              new Function<Class<?>, String>() {
                                @Override
                                public String apply(Class<?> aClass) {
                                  return getStubClassSignature(aClass);
                                }
                              }
          )
      ));
    }

    sb.append(" {").append("\n");

    // Stub all nested classes, enums and interfaces
    Class<?>[] classes = classToStub.getClasses();
    for (Class<?> aClass : classes) {
      sb.append(getStubForClass(aClass)).append("\n");
    }

    // Stub all declared methods
    for (Method method : classToStub.getDeclaredMethods()) {
      sb.append(getModifiersSignature(method.getModifiers()));

      if (method.getReturnType() == null) {
        sb.append("void");
      } else {
        sb.append(getStubClassSignature(method.getReturnType()));
      }
      sb.append(" ").append(method.getName()).append("(");
      Class<?>[] parameterTypes = method.getParameterTypes();
      for (int i = 0; i < parameterTypes.length; i++) {
        sb.append(parameterTypes[i].getCanonicalName());
        sb.append(" arg").append(i);
        if (i < parameterTypes.length - 1) {
          sb.append(", ");
        }
      }
      sb.append(") ");
      Class<?>[] exceptionTypes = method.getExceptionTypes();
      if (exceptionTypes.length > 0) {
        for (int i = 0; i < exceptionTypes.length; i++) {
          sb.append(getStubClassSignature(exceptionTypes[i]));
          if (i < exceptionTypes.length - 1) {
            sb.append(", ");
          }
        }
        sb.append(" ");
      }

      sb.append("{");
      sb.append(" throw new java.lang.UnsupportedOperationException(); ");
      sb.append("}\n");
    }

    sb.append("}\n");
    return sb.toString();
  }

  /**
   * Get the modifiers signature for the specified modifiers bit set.
   *
   * @param modifiers The modifier bit set
   */
  private static String getModifiersSignature(int modifiers) {
    StringBuilder sb = new StringBuilder();
    if (Modifier.isPublic(modifiers)) {
      sb.append("public ");
    } else if (Modifier.isProtected(modifiers)) {
      sb.append("protected ");
    } else if (Modifier.isPrivate(modifiers)) {
      sb.append("private ");
    }

    if (Modifier.isAbstract(modifiers)) {
      sb.append("abstract ");
    }

    if (Modifier.isStatic(modifiers)) {
      sb.append("static ");
    }

    if (Modifier.isFinal(modifiers)) {
      sb.append("final ");
    }
    return sb.toString();
  }
}
