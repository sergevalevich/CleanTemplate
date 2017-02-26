package com.valevich.umora.injection;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public final class Injector<T>  {

    private final Class<T> componentClass;
    private final T component;
    private final HashMap<Class<?>, Method> methods;

    public Injector(Class<T> componentClass, T component) {
        this.componentClass = componentClass;
        this.component = component;
        this.methods = getMethods(componentClass);
    }

    public T getComponent() {
        return component;
    }

    public void inject(Object target) {

        Class targetClass = target.getClass();
        Method method = methods.get(targetClass);
        while (method == null && targetClass != null) {
            targetClass = targetClass.getSuperclass();
            method = methods.get(targetClass);
        }

        if (method == null)
            throw new RuntimeException(String.format("No %s injecting method exists in %s component", target.getClass(), componentClass));

        try {
            method.invoke(component, target);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final ConcurrentHashMap<Class<?>, HashMap<Class<?>, Method>> cache = new ConcurrentHashMap<>();

    private static HashMap<Class<?>, Method> getMethods(Class componentClass) {
        HashMap<Class<?>, Method> methods = cache.get(componentClass);
        if (methods == null) {
            synchronized (cache) {
                methods = cache.get(componentClass);
                if (methods == null) {
                    methods = new HashMap<>();
                    for (Method method : componentClass.getMethods()) {
                        Class<?>[] params = method.getParameterTypes();
                        if (params.length == 1)
                            methods.put(params[0], method);
                    }
                    cache.put(componentClass, methods);
                }
            }
        }
        return methods;
    }
}
