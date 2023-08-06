package core.jcommandbuilder.JArgument;

import core.jcommandbuilder.JTabProvider;
import jline.internal.Nullable;

public abstract class JArgument<T> {
    protected static final JArgumentValidateResponse VALID = new JArgumentValidateResponse(false, "");

    public JArgument(String argName) {
        this.argName = argName;
    }

    private final String argName;
    private JTabProvider tabProvider;

    private T userSetValue;
    private T defaultValue;

    /**
     * Sets the default value of the arg that will be used is not value is set.
     * @param rawGivenArg The raw string that was given for the arg in the command.
     * @return this as a builder object.
     */
    public JArgument<T> setDefault(String rawGivenArg){
        defaultValue = convert(rawGivenArg);
      return this;
    }
    /**
     * Sets the value of the arg with the correct type T
     * @param rawGivenArg The raw string that was given for the arg in the command.
     * @return this as a builder object.
     */
    public JArgument<T> setValue(String rawGivenArg){
        userSetValue = convert(rawGivenArg);
        return this;
    }


    public JArgument<T> setTabProvider(JTabProvider tabProvider){
        this.tabProvider = tabProvider;
        return this;
    }
    /**
     * Will convert the String in to an object of type T
     * @param rawGivenArg The raw string that was given for the arg in the command.
     * @return The converted object to be of type T
     */
    protected abstract T convert(String rawGivenArg);

    /**
     * Checks that the given string can be converted in to type T
     * If not the reason why will be sent to the command sender.
     * @param rawGivenArg The raw string that was given for the arg in the command.
     * @return JArgumentValidateResponse
     */
    public abstract JArgumentValidateResponse validate(String rawGivenArg);

    public void reset(){
        userSetValue = null;
    }

    public boolean hasDefaultValue(){
        return defaultValue != null;
    }

    public T getValue(){
        if (userSetValue == null)
            return defaultValue;


        return userSetValue;
    }

    public String getNameWithBrackets(){
        return (hasDefaultValue() ? "&8[" : "&7<") + argName + (hasDefaultValue() ? "]" : ">");
    }

    //----------------------------------------------------
    // [Default] -> Getters and Setters
    //----------------------------------------------------

    @Nullable
    public JTabProvider getTabProvider() {
        return tabProvider;
    }

    public String getArgName() {
        return argName;
    }

    public record JArgumentValidateResponse(boolean wasInvalid, String reason){}

}
