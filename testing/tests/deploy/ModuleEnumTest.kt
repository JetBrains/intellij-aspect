/*
 * Copyright 2026 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.aspect.testing.tests.deploy

import com.google.common.truth.Truth.assertWithMessage
import com.intellij.aspect.lib.Modules
import com.intellij.aspect.lib.Rules
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ModuleEnumTest {

  @Test
  fun testModuleRulesets() {
    val supportedRulesets = Rules.entries.map { it.rulesetName }.toSet()

    Modules.entries.forEach { module ->
      assertWithMessage("module %s declares unsupported rulesets: %s", module.name, module.rulesets)
        .that(supportedRulesets)
        .containsAtLeastElementsIn(module.rulesets)
    }
  }
}
