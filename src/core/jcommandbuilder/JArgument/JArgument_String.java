package core.jcommandbuilder.JArgument;

public class JArgument_String extends JArgument<String> {
    public JArgument_String(String argName) {
        super(argName);
    }

    @Override
    protected String convert(String rawGivenArg) {
        return rawGivenArg;
    }

    @Override
    public JArgumentValidateResponse validate(String rawGivenArg) {
        return VALID;
    }
}
