package core.jcommandbuilder.JArgument;

import core.utils.Util;

public class JArgument_Int extends JArgument<Integer> {
    public JArgument_Int(String argName) {
        super(argName);
    }

    @Override
    protected Integer convert(String rawGivenArg) {
        return Integer.parseInt(rawGivenArg);
    }

    @Override
    public JArgumentValidateResponse validate(String rawGivenArg) {
        if (Util.NUMBER.isNotInt(rawGivenArg)) {
            return new JArgumentValidateResponse(true, getNameWithBrackets() + " &cmust be a number.");
        }

        return VALID;
    }

}
