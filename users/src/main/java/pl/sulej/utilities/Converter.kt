package pl.sulej.utilities

interface Converter<Input, Output> {

    fun convert(input: Input): Output
}