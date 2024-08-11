package io.kotest.plugin.intellij.intentions

import com.intellij.openapi.command.CommandProcessor
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import io.kotest.matchers.shouldBe
import org.jetbrains.kotlin.idea.core.moveCaret
import org.jetbrains.kotlin.idea.util.application.runWriteAction
import java.nio.file.Paths

class AssertSoftlyIntentionTest : BasePlatformTestCase() {

   override fun getTestDataPath(): String {
      val path = Paths.get("./src/test/resources/").toAbsolutePath()
      return path.toString()
   }

   fun testIntentionForPartialSelectedMultipleLines() {

      myFixture.configureByFiles(
         "/intentions/assert_softly.kt",
         "/io/kotest/core/spec/style/specs.kt"
      )

      editor.moveCaret(905)
      editor.selectionModel.setSelection(905, 955)

      val intention = myFixture.findSingleIntention("Surround statements with soft assert")
      intention.familyName shouldBe "Surround statements with soft assert"

      CommandProcessor.getInstance().runUndoTransparentAction {
         runWriteAction {
            intention.invoke(project, editor, file)
         }
      }

      file.text shouldBe """package io.kotest.samples.gradle

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldBeLowerCase
import io.kotest.matchers.string.shouldBeUpperCase
import io.kotest.matchers.string.shouldNotBeBlank
import io.kotest.assertions.assert.assertSoftly

class FunSpecExampleTest : FunSpec({

   test("a string cannot be blank") {
      "wibble".shouldNotBeBlank()
   }

   test("a string should be lower case").config(enabled = true) {
      "wibble".shouldBeLowerCase()
   }

   context("some context") {

      test("a string cannot be blank") {
         "wibble".shouldNotBeBlank()
      }

      test("a string should be lower case").config(enabled = true) {
         "wibble".shouldBeLowerCase()
      }

      context("another context") {

         test("a string cannot be blank") {
            "wibble".shouldNotBeBlank()
         }

         test("a string should be lower case").config(enabled = true) {
            assertSoftly {
              "wibble".shouldBeLowerCase()
              "WOBBLE".shouldBeUpperCase()
            }
         }
      }
   }

})
"""
   }

   fun testIntentionForFullLine() {

      myFixture.configureByFiles(
         "/intentions/assert_softly.kt",
         "/io/kotest/core/spec/style/specs.kt"
      )

      editor.moveCaret(523)
      editor.selectionModel.setSelection(523, 558)

      val intention = myFixture.findSingleIntention("Surround statements with soft assert")
      intention.familyName shouldBe "Surround statements with soft assert"

      CommandProcessor.getInstance().runUndoTransparentAction {
         runWriteAction {
            intention.invoke(project, editor, file)
         }
      }

      file.text shouldBe """package io.kotest.samples.gradle

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldBeLowerCase
import io.kotest.matchers.string.shouldBeUpperCase
import io.kotest.matchers.string.shouldNotBeBlank
import io.kotest.assertions.assert.assertSoftly

class FunSpecExampleTest : FunSpec({

   test("a string cannot be blank") {
      "wibble".shouldNotBeBlank()
   }

   test("a string should be lower case").config(enabled = true) {
      "wibble".shouldBeLowerCase()
   }

   context("some context") {

      test("a string cannot be blank") {
         assertSoftly {
           "wibble".shouldNotBeBlank()
         }
      }

      test("a string should be lower case").config(enabled = true) {
         "wibble".shouldBeLowerCase()
      }

      context("another context") {

         test("a string cannot be blank") {
            "wibble".shouldNotBeBlank()
         }

         test("a string should be lower case").config(enabled = true) {
            "wibble".shouldBeLowerCase()
            "WOBBLE".shouldBeUpperCase()
         }
      }
   }

})
"""
   }
}
