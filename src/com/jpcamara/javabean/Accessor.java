package com.jpcamara.javabean;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Accessor {
  private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
  private static final Class<?>[] MAP_CLASS_ARRAY = new Class<?>[] { Object.class };
  
  private static final Map<String, Method> methods = new ConcurrentHashMap<String, Method>();
  
  public static Method findGetter(Object currentObject, String name) {
    Class<?> findOn = currentObject.getClass();
    Method method = methods.get(findOn.getName() + name);
    try {
      method.getName();
    } catch (NullPointerException npe) {
      String methodName = javacase(name);
      try {
        if (currentObject instanceof Map) {
          method = findOn.getMethod("get", MAP_CLASS_ARRAY);
        } else {
          method = findOn.getMethod("get" + methodName, EMPTY_CLASS_ARRAY);
        }
      } catch (NoSuchMethodException e) {
        try {
          method = findOn.getMethod("get" + methodName + "s", EMPTY_CLASS_ARRAY);
        } catch (NoSuchMethodException ee) {
          try {
            method = findOn.getMethod("is" + methodName, EMPTY_CLASS_ARRAY);
          } catch (NoSuchMethodException eee) {
            try {
              method = findOn.getMethod("has" + methodName, EMPTY_CLASS_ARRAY);
            } catch (NoSuchMethodException eeee) {
              try {
                method = findOn.getMethod(name, EMPTY_CLASS_ARRAY);
              } catch (NoSuchMethodException eeeee) {
                method = null;
              }
            }
          }
        }
      }
    }
    if (method != null) {
      methods.put(findOn.getName() + name, method);
    }
    return method;
  }
  
  private static String javacase(String name) {
    if (name.length() == 1) {
      return name.toUpperCase();
    }
    return name.substring(0, 1).toUpperCase() + name.substring(1);
  }
}