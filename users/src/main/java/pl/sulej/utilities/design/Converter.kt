package pl.sulej.utilities.design

interface Converter<Input, Output> {

    fun convert(input: Input): Output
}