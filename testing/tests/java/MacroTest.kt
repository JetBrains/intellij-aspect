package com.intellij.aspect.testing.tests.java

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MacroTest {

    @Rule
    @JvmField
    val aspect = AspectFixture()

    @Test
    fun testFindFoo() {
        val target = aspect.findTarget("//:Foo")
        assertThat(target.hasJavaIdeInfo()).isTrue()
        assertThat(target.kind).isEqualTo("java_library")
        assertThat(target.executable).isFalse()
        assertThat(target.generatorName).isEqualTo("LotsOfLibraries")
        assertThat(target.javaIdeInfo.sourcesCount).isEqualTo(1)
        assertThat(target.javaIdeInfo.sourcesList[0].relativePath).isEqualTo("Foo.java")
    }

    @Test
    fun testFindBar() {
        val target = aspect.findTarget("//:Bar")
        assertThat(target.hasJavaIdeInfo()).isTrue()
        assertThat(target.kind).isEqualTo("java_library")
        assertThat(target.executable).isFalse()
        assertThat(target.generatorName).isEqualTo("LotsOfLibraries")
        assertThat(target.javaIdeInfo.sourcesCount).isEqualTo(1)
        assertThat(target.javaIdeInfo.sourcesList[0].relativePath).isEqualTo("Bar.java")
    }

}