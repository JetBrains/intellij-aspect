package com.example

import org.jetbrains.annotations.PropertyKey

class Module {

  fun incorrectBundle(
    @PropertyKey(resourceBundle = "messages.IncorrectBundle") key: String,
  ) {
  }

  fun fooBundle(
    @PropertyKey(resourceBundle = "messages.FooBundle") key: String,
  ) {
  }

  fun barBundle(
    @PropertyKey(resourceBundle = "messages.BarBundle") key: String,
  ) {
  }

  fun test() {
    fooBundle("keyFoo")
    fooBundle("keyBar")

    barBundle("keyFoo")
    barBundle("keyBar")
  }
}
