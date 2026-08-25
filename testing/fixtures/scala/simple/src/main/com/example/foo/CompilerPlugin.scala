package com.example.foo

import scala.tools.nsc.Global
import scala.tools.nsc.plugins.Plugin
import scala.tools.nsc.plugins.PluginComponent

final class CompilerPlugin(val global: Global) extends Plugin {
  override val name = "fixture-plugin"
  override val description = "Compiler plugin used by the aspect test fixture"
  override val components: List[PluginComponent] = Nil
}
