/*
 * Copyright 2000-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.testGuiFramework.recorder.actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.testGuiFramework.recorder.compile.KotlinCompileUtil
import com.intellij.testGuiFramework.recorder.components.GuiRecorderComponent
import com.intellij.testGuiFramework.recorder.ui.Notifier

/**
 * @author Sergey Karashevich
 */

class PerformScriptAction : AnAction(null, "Run GUI Script", AllIcons.Actions.Execute) {

  companion object {
    val LOG = Logger.getInstance(PerformScriptAction::class.java)
  }

  override fun actionPerformed(p0: AnActionEvent?) {
    LOG.info("Compile and evaluate current script buffer")
    Notifier.updateStatus("${Notifier.LONG_OPERATION_PREFIX}Compiling and performing current script")
    val editor = GuiRecorderComponent.getEditor()

    KotlinCompileUtil.compileAndRun(editor.document.text)
  }

}
