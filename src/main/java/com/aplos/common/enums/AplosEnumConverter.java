package com.aplos.common.enums;

import javax.faces.component.PartialStateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.sun.faces.util.MessageFactory;

public class AplosEnumConverter implements Converter, PartialStateHolder {

    // for StateHolder
    public AplosEnumConverter() {

    }

    public AplosEnumConverter(Class targetClass) {
        this.targetClass = (Class<? extends Enum>) targetClass;
    }

    // ------------------------------------------------------ Manifest Constants


    /**
     * <p>The standard converter id for this converter.</p>
     */
    public static final String CONVERTER_ID = "javax.faces.Enum";

    /**
     * <p>The message identifier of the {@link javax.faces.application.FacesMessage} to be created if
     * the conversion to <code>Enum</code> fails.  The message format
     * string for this message may optionally include the following
     * placeholders:
     * <ul>
     * <li><code>{0}</code> replaced by the unconverted value.</li>
     * <li><code>{1}</code> replaced by one of the enum constants or the empty
     * string if none can be found.</li>
     * <li><code>{2}</code> replaced by a <code>String</code> whose value
     * is the label of the input component that produced this message.</li>
     * </ul></p>
     */
    public static final String ENUM_ID =
         "javax.faces.converter.EnumConverter.ENUM";

    /**
     * <p>The message identifier of the {@link javax.faces.application.FacesMessage} to be created if
     * the conversion to <code>Enum</code> fails and no target class has been
     * provided.  The message format
     * string for this message may optionally include the following
     * placeholders:
     * <ul>
     * <li><code>{0}</code> replaced by the unconverted value.</li>
     * <li><code>{1}</code> replaced by a <code>String</code> whose value
     * is the label of the input component that produced this message.</li>
     * </ul></p>
     */
    public static final String ENUM_NO_CLASS_ID =
         "javax.faces.converter.EnumConverter.ENUM_NO_CLASS";

    // ----------------------------------------------------- Converter Methods

    private Class<? extends Enum> targetClass;


    /**
     * <p>Convert the <code>value</code> argument to one of the enum
     * constants of the class provided in our constructor.  If no
     * target class argument has been provided to the constructor of
     * this instance, throw a <code>ConverterException</code>
     * containing the {@link #ENUM_NO_CLASS_ID} message with proper
     * parameters.  If the <code>value</code> argument is <code>null</code>
     * or it  has a length of zero, return <code>null</code>.
     * Otherwise, perform the equivalent of <code>Enum.valueOf</code> using
     * target class and <code>value</code> and return the <code>Object</code>.
     * If the conversion fails, throw a <code>ConverterException</code>
     * containing the {@link #ENUM_ID} message with proper parameters.
     * </p>
     *
     * @param context   the <code>FacesContext</code> for this request.
     * @param component the <code>UIComponent</code> to which this value
     *                  will be applied.
     * @param value     the String <code>value</code> to be converted to
     *                  <code>Object</code>.
     * @throws ConverterException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public Object getAsObject(FacesContext context, UIComponent component,
                              String value) {

        if (context == null || component == null) {
            throw new NullPointerException();
        }

        if (targetClass == null) {
            throw new ConverterException(
                 MessageFactory.getMessage(context,
                      ENUM_NO_CLASS_ID,
                      value,
                      MessageFactory.getLabel(context,
                           component)));
        }

        // If the specified value is null or zero-length, return null
        if (value == null) {
            return (null);
        }
        value = value.trim();
        if (value.length() < 1) {
            return (null);
        }

        try {
            return Enum.valueOf(targetClass, value);
        } catch (IllegalArgumentException iae) {
            throw new ConverterException(
                 MessageFactory.getMessage(context,
                      ENUM_ID,
                      value,
                      value,
                      MessageFactory.getLabel(context,
                           component)), iae);
        }

    }

    /**
     * <p>Convert the enum constant given by the <code>value</code>
     * argument into a String.  If no target class argument has been
     * provided to the constructor of this instance, throw a
     * <code>ConverterException</code> containing the {@link
     * #ENUM_NO_CLASS_ID} message with proper parameters. If the
     * <code>value</code> argument is <code>null</code>, return
     * <code>null</code>.  If the value is an instance of the provided
     * target class, return its string value by <span
     * class="changed_added_2_0">casting it to a
     * <code>java.lang.Enum</code> and returning the result of calling
     * the <code>name()</code> method.</span> Otherwise, throw a {@link
     * ConverterException} containing the {@link #ENUM_ID} message with
     * proper parameters.</p>
     *
     * @throws ConverterException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public String getAsString(FacesContext context, UIComponent component,
                              Object value) {

        if (context == null || component == null) {
            throw new NullPointerException();
        }

        if (targetClass == null) {
            throw new ConverterException(
                 MessageFactory.getMessage(context,
                      ENUM_NO_CLASS_ID,
                      value,
                      MessageFactory.getLabel(context,
                           component)));
        }

        // If the specified value is null, return null
        if (value == null) {
            return "";
        }

        if (targetClass.isInstance(value)) {
            return ((Enum)value).name();
        }

        throw new ConverterException(
             MessageFactory.getMessage(context,
                  ENUM_ID,
                  value,
                  value,
                  MessageFactory.getLabel(context,
                       component)));
    }

    // ----------------------------------------------------------- StateHolder

    public void restoreState(FacesContext facesContext, Object object) {
        if (facesContext == null) {
            throw new NullPointerException();
        }
        if (object != null) {
            this.targetClass = (Class<? extends Enum>) object;
        }
    }

    public Object saveState(FacesContext facesContext) {
        if (facesContext == null) {
            throw new NullPointerException();
        }
        if (!initialStateMarked()) {
            return this.targetClass;
        }
        return null;
    }

    private boolean isTransient = false;

    public void setTransient(boolean b) {
        isTransient = b;
    }

    public boolean isTransient() {
        return isTransient;
    }

    private boolean initialState;

    public void markInitialState() {
        initialState = true;
    }

    public boolean initialStateMarked() {
        return initialState;
    }

    public void clearInitialState() {
        initialState = false;
    }
}
