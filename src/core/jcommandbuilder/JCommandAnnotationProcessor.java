package core.jcommandbuilder;

import core.jcommandbuilder.JArgument.JArg;
import core.jcommandbuilder.JArgument.JArgument;
import core.utils.CaseInsensitiveString;
import core.utils.Util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;


public class JCommandAnnotationProcessor {

    public final JCommandManager jCommandManager;

    public JCommandAnnotationProcessor(JCommandManager jCommandManager) {
        this.jCommandManager = jCommandManager;
    }

    public void registerCommand(Object commandClassInstance) {
        Class<?> clazz = commandClassInstance.getClass();

            for (Method method : clazz.getMethods())
                checkMethodForCommand(method, clazz, commandClassInstance);

    }

    /**
     * Checks the method to see if there is a command associated with the method.
     */
    private void checkMethodForCommand(Method method, Class<?> commandClass, Object commandClassInstance) {
        if (!method.isAnnotationPresent(JCommand.class)) return;

        for (String commandNames : method.getAnnotation(JCommand.class).value()) {
            CaseInsensitiveString commandName = new CaseInsensitiveString(commandNames);
            boolean playerOnlyCommand = method.isAnnotationPresent(JCommandPlayerOnly.class);
            boolean consoleOnlyCommand = method.isAnnotationPresent(JCommandConsoleOnly.class);
            String permission = "NO_PERMISSION";

            if (method.isAnnotationPresent(JCommandPermission.class)){
                permission = method.getAnnotation(JCommandPermission.class).value();
            }

            ArrayList<CaseInsensitiveString> subCommands = new ArrayList<>();

            if (method.isAnnotationPresent(JSubCommand.class))
                for (String definedSubCommands : method.getAnnotation(JSubCommand.class).value())
                    subCommands.add(new CaseInsensitiveString(definedSubCommands));
            else
                subCommands.add(new CaseInsensitiveString("*"));

            for (CaseInsensitiveString subCommand : subCommands){
                CaseInsensitiveString commandID = new CaseInsensitiveString(commandName + "-");
                commandID.add(subCommand.getValue());

                ArrayList<JArgument<?>> jArguments = convertAllJArgToJArguments(method);

                JCmd jCmd = new JCmd(method, commandClassInstance, commandName, subCommand, jArguments.toArray(new JArgument[]{}), playerOnlyCommand, consoleOnlyCommand, permission);

                if (!validateMethodParameters(method, commandClass, jArguments))
                    jCmd.setFailedToLoad();

                jCommandManager.registerCommand(commandName, commandID, jCmd);
            }
        }
    }

    /**
     * Checks that the methods parameters match the required parameters set by the annotations.
     */
    private boolean validateMethodParameters(Method method, Class<?> commandClass, ArrayList<JArgument<?>> arguments) {
        Class<?>[] parameters = method.getParameterTypes();

        boolean argumentsInvalid = false;
        StringBuilder argumentParameters = new StringBuilder();

        int parameterTracker = 1;
        if (parameters.length-1 < arguments.size()) {
            Util.ERROR_REPORTER.reportToConsole(
                    "Incorrect parameter amount for command method " + method.getName() + " in class " + commandClass.getSimpleName(),
                    "Expected amount of parameters: " + parameters.length + " Parameters received: " + (arguments.size()-1));
            return false;
        }

        for (JArgument<?> jArgument : arguments) {
            // -1 because JCommandSender is the 1st parameter.

            //Checks that the method parameters are correct for the args given.
            String jArgumetTypeString = ((ParameterizedType)jArgument.getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            String paramaterTypeString = parameters[parameterTracker].getTypeName();

            boolean isSameParameterSameType = jArgumetTypeString.equalsIgnoreCase(paramaterTypeString);

            //Because primitives have the primitive type and the wrapper type detect either or.
            if (jArgumetTypeString.equalsIgnoreCase("java.lang.Integer") && paramaterTypeString.toUpperCase().matches("JAVA.LANG.INTEGER|INT")) isSameParameterSameType = true;

            if (parameters.length - 1 < arguments.size() || !isSameParameterSameType)
                argumentsInvalid = true;

            parameterTracker++;
            argumentParameters.append(", ").append(jArgumetTypeString);
        }

        if (parameters[0] != JCommandSender.class || argumentsInvalid) {
            Util.ERROR_REPORTER.reportToConsole(
                    "Incorrect method parameters for command method " + method.getName() + " in class " + commandClass.getSimpleName(),
                    "Parameters should be (JCommandSender" + argumentParameters + ")");
            return false;
        }

        return true;
    }

    /**
     * Will convert all the JArg annotations in to JArgumrent classes.
     */
    private ArrayList<JArgument<?>> convertAllJArgToJArguments(Method method) {
        ArrayList<JArgument<?>> jArguments = new ArrayList<>();

        for (Annotation annotation : method.getAnnotations()) {

            if (annotation instanceof JArg jArg)
                jArguments.add(convertJArgToJArguments(jArg));

            if (annotation instanceof JArg.JArgs jArgs)
                for (JArg jArg : jArgs.value())
                    jArguments.add(convertJArgToJArguments(jArg));

        }
        return jArguments;
    }

    private JArgument<?> convertJArgToJArguments(JArg jArg) {
        try {
            JArgument<?> jArgument = jArg.type().getConstructor(String.class).newInstance(jArg.name());

            if (!jArg.defaultValue().equalsIgnoreCase("Not Set"))
                jArgument.setDefault(jArg.defaultValue());

            if (!jArg.tabProvider().equalsIgnoreCase("Not Set"))
                if (jCommandManager.hasTabProvider(jArg.tabProvider()))
                    jArgument.setTabProvider(jCommandManager.getTabProvider(jArg.tabProvider()));

            return jArgument;

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
