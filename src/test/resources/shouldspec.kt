package com.sksamuel.kotest.specs.shouldspec

import io.kotest.core.spec.style.ShouldSpec

class ShouldSpecExample : ShouldSpec() {
  init {
    should("top level test") {
      // test here
    }
    should("top level test with config").config(enabled = true) {
      // test here
    }
  }
}
