package com.assessment.myRetail.application.logging

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class KeyValueLoggerSpec extends Specification {

    KeyValueLogger sut = new KeyValueLogger()

    void "transforms #input with #params"() {
        expect:
        sut.log(input, params) == expectedOutput

        where:
        input         | params                                     | expectedOutput
        ''            | [:]                                        | 'message='
        'foo'         | [:]                                        | 'message=foo'
        'foo'         | [elapsedTime: 100]                         | 'message=foo, elapsedTime=100'
        'Hello World' | [elapsedTime: 100, foo: "bar"]             | 'message="Hello World", elapsedTime=100, foo=bar'
        'Hello World' | [elapsedTime: 100, foo: null]              | 'message="Hello World", elapsedTime=100, foo='
        'Hello World' | [elapsedTime: 100, without: 'ab']          | 'message="Hello World", elapsedTime=100, without=ab'
        'Hello World' | [elapsedTime: 100, withSpace: ' a b ']     | 'message="Hello World", elapsedTime=100, withSpace=" a b "'
        'Hello World' | [elapsedTime: 100, withColon: 'a:b']       | 'message="Hello World", elapsedTime=100, withColon="a:b"'
        'Hello World' | [elapsedTime: 100, withEqual: 'a=b']       | 'message="Hello World", elapsedTime=100, withEqual="a=b"'
        'Hello World' | [elapsedTime: 100, withComma: 'a,b']       | 'message="Hello World", elapsedTime=100, withComma="a,b"'
        'Hello World' | [elapsedTime: 100, withDoubleQuote: 'a"b'] | 'message="Hello World", elapsedTime=100, withDoubleQuote="a\\"b"'
        'Hello World' | [elapsedTime: 100, withSemicolon: 'a;b']   | 'message="Hello World", elapsedTime=100, withSemicolon="a;b"'
        'Hello World' | [elapsedTime: 100, withDollarSign: 'a$b']  | 'message="Hello World", elapsedTime=100, withDollarSign="a$b"'
        'Hello World' | [elapsedTime: 100, withPipe: 'a|b']        | 'message="Hello World", elapsedTime=100, withPipe="a|b"'
        'Hello World' | [elapsedTime: 100, withMulti: ' a,b:=']    | 'message="Hello World", elapsedTime=100, withMulti=" a,b:="'
        ''            | [a: true]                                  | 'message=, a=true'
        ''            | [a: false]                                 | 'message=, a=false'
        ''            | [a: "a${'a' + 1}b"]                        | 'message=, a=aa1b'
        'Hello World' | null                                       | 'message="Hello World"'
    }

    void "transforms #input without params"() {
        expect:
        sut.log(input) == expectedOutput

        where:
        input         | expectedOutput
        ''            | 'message='
        'Hello World' | 'message="Hello World"'
    }

}
