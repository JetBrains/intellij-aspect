package com.intellij.aspect.testing.tests.java

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.testing.rules.AspectFixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SimpleTest {

    @Rule
    @JvmField
    val aspect = AspectFixture()

    @Test
    fun testFindsMain() {
        val target = aspect.findTarget("//:main")
        assertThat(target.hasJavaIdeInfo()).isTrue()
        assertThat(target.kindString).isEqualTo("java_binary")

        // Sources are reported correctly
        assertThat(target.javaIdeInfo.sourcesList.size).isEqualTo(1)
        assertThat(target.javaIdeInfo.sourcesList[0].isSource).isTrue()
        assertThat(target.javaIdeInfo.sourcesList[0].relativePath).isEqualTo("Main.java")

        // Dependencies are reported correctly
        assertThat(target.depsList.map { it.target.label }).contains("//lib:util")

        // JVM-info is reported correctly
        val jvmInfo = target.javaIdeInfo.jvmTargetInfo
        assertThat(jvmInfo.mainClass).isEqualTo("com.intellij.aspect.testing.fixtures.java.simple.Main")

        // The toolchain dependency is reported
        val toolchains = target.depsList.map { aspect.findTarget(it.target.label) }.filter { it.hasJavaToolchainIdeInfo() }
        assertThat(toolchains).isNotEmpty()
        assertThat(toolchains.first().javaToolchainIdeInfo.sourceVersion).isEqualTo("21")
        assertThat(toolchains.first().javaToolchainIdeInfo.javaHome).isNotEmpty()
    }

    @Test
    fun testFindsLib() {
        val target = aspect.findTarget("//lib:util")
        assertThat(target.hasJavaIdeInfo()).isTrue()
        assertThat(target.kindString).isEqualTo("java_library")

        // Sources are reported correctly
        assertThat(target.javaIdeInfo.sourcesList.size).isEqualTo(1)
        assertThat(target.javaIdeInfo.sourcesList[0].isSource).isTrue()
        assertThat(target.javaIdeInfo.sourcesList[0].relativePath).isEqualTo("lib/Util.java")

        // JVM-info is reported correctly
        val jvmInfo = target.javaIdeInfo.jvmTargetInfo
        assertThat(jvmInfo.javacOptsList).isEqualTo(listOf("-Xep:ReturnValueIgnored:WARN"))
        assertThat(jvmInfo.jars.binaryJarsList.size).isEqualTo(1)
        assertThat(jvmInfo.jars.binaryJarsList[0].relativePath).startsWith("lib/")
        assertThat(jvmInfo.jars.sourceJarsList.size).isEqualTo(1)
        assertThat(jvmInfo.jars.interfaceJarsList.size).isEqualTo(1)
        assertThat(jvmInfo.jars.jdepsList.size).isAtMost(1)
        assertThat(jvmInfo.hasApiGeneratingPlugins).isFalse()

        // The toolchain dependency is reported
        val toolchains = target.depsList.map { aspect.findTarget(it.target.label) }.filter { it.hasJavaToolchainIdeInfo() }
        assertThat(toolchains).isNotEmpty()
        assertThat(toolchains.first().javaToolchainIdeInfo.sourceVersion).isEqualTo("21")
        assertThat(toolchains.first().javaToolchainIdeInfo.javaHome).isNotEmpty()
    }
}