package com.aplos.common.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.sun.faces.facelets.tag.AbstractTagLibrary;


public final class JsfUtilLibrary extends AbstractTagLibrary {

    public static final String NAMESPACE = "http://www.aplossystems.co.uk/jsfUtil";

    /**  Current instance of library. */
    public static final JsfUtilLibrary INSTANCE = new JsfUtilLibrary();

    public JsfUtilLibrary() {
        super(NAMESPACE);

        try {
            Method[] methods = JSFUtil.class.getMethods();

            for (int i = 0; i < methods.length; i++) {
                if (Modifier.isStatic(methods[i].getModifiers())) {
                    this.addFunction(methods[i].getName(), methods[i]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}