package com.example.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration

class HelloProcessor(private val codeGenerator: CodeGenerator) : SymbolProcessor {
  override fun process(resolver: Resolver): List<KSAnnotated> {
    resolver
      .getSymbolsWithAnnotation("com.example.processor.GenerateHello")
      .filterIsInstance<KSClassDeclaration>()
      .forEach { declaration ->
        val packageName = declaration.packageName.asString()
        val className = declaration.simpleName.asString()
        val helloName = "${className}Hello"
        val containingFile = declaration.containingFile ?: return@forEach

        codeGenerator
          .createNewFile(Dependencies(aggregating = false, containingFile), packageName, helloName)
          .bufferedWriter()
          .use { writer ->
            if (packageName.isNotEmpty()) {
              writer.write("package $packageName\n\n")
            }
            writer.write("object $helloName {\n")
            writer.write("  fun greeting(): String = \"Hello from $className\"\n")
            writer.write("}\n")
          }
      }
    return emptyList()
  }
}

class HelloProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
    HelloProcessor(environment.codeGenerator)
}
