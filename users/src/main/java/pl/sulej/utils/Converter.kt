package pl.sulej.utils

interface Converter<Input, Output> {

    fun convert(input: Input): Output
}