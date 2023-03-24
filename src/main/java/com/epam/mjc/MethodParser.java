package com.epam.mjc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        int argumentsBeginIndex = signatureString.indexOf("(") + 1;
        int argumentsEndIndex = signatureString.indexOf(")");
        String[] arguments = signatureString.substring(argumentsBeginIndex, argumentsEndIndex)
                .split(", ");
        String[] nonArguments = signatureString.substring(0, argumentsBeginIndex - 1)
                .split(" ");

        boolean accessModifierIsExplicit = nonArguments.length > 2;
        String accessModifier = accessModifierIsExplicit ? nonArguments[0] : null;

        String returnType = accessModifierIsExplicit ? nonArguments[1] : nonArguments[0];

        String methodName = accessModifierIsExplicit ? nonArguments[2] : nonArguments[1];

        List<MethodSignature.Argument> argumentsList = Stream.of(arguments)
                .filter(a -> a.length() > 0)
                .map(argument -> {
                    int spaceIndex = argument.indexOf(" ");
                    String type = argument.substring(0, spaceIndex);
                    String name = argument.substring(spaceIndex + 1);
                    return new MethodSignature.Argument(type, name);
                })
                .collect(Collectors.toList());

        MethodSignature signature = new MethodSignature(methodName, argumentsList);
        signature.setAccessModifier(accessModifier);
        signature.setReturnType(returnType);

        return signature;
    }
}
