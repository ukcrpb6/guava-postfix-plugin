guava-postfix-plugin
====================

Guava postfix plugin for IntelliJ

Available templates:

|   Postfix Expression  | Description                                                                                                            | Example                                |
| --------------------- | ---------------------------------------------------------------------------------------------------------------------- | -------------------------------------- |
| .checkArgument        | Checks that the boolean is true                                                                                        | checkArgument(expr)                    |
| .checkNotNull         | Checks that the value is not null                                                                                      | checkNotNull(expr)                     |
| .checkState           | Checks some state of the object, not dependent on the method arguments                                                 | checkState(expr)                       |
| .checkElementIndex    | Checks that index is a valid element index into a list, string, or array with the specified size                       | checkElementIndex(index, size)         |
| .checkPositionIndex   | Checks that index is a valid position index into a list, string, or array with the specified size                      | checkPositionIndex(index, size)        |
| .checkPositionIndexes | Checks that [start, end) is a valid sub range of a list, string, or array with the specified size                      | checkPositionIndexes(start, end, size) |
| --------------------- | ---------------------------------------------------------------------------------------------------------------------- | -------------------------------------- |
| .join                 | Joins pieces of text (specified as an array, Iterable, varargs or even a Map) with a separator                         | Joiner.on(',').join(iterable)          |
| .split                | Extracts non-overlapping substrings from an input string, typically by recognizing appearances of a separator sequence | Splitter.on(',').split(charsequence)   |
| --------------------- | ---------------------------------------------------------------------------------------------------------------------- | -------------------------------------- |
| .immutableListCopyOf  | Creates an immutable list containing the given elements, in order                                                      | ImmutableList.copyOf(Iterable)         |
| .immutableListOf      | Creates an immutable list containing a single element                                                                  | ImmutableList.of(Iterable)             |
| .immutableSetCopyOf   | Creates an immutable set containing the given elements, in order                                                       | ImmutableSet.copyOf(Iterable)          |
| .immutableSetOf       | Creates an immutable set containing a single element                                                                   | ImmutableSet.of(Object)                |
| --------------------- | ---------------------------------------------------------------------------------------------------------------------- | -------------------------------------- |
| .fluentIterable       | Creates a fluent iterable that wraps iterable, or iterable itself if it is already a FluentIterable                    | FluentIterable.from(Iterable)          |
| --------------------- | ---------------------------------------------------------------------------------------------------------------------- | -------------------------------------- |
