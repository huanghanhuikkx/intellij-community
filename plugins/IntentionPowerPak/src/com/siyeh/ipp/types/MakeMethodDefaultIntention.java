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
package com.siyeh.ipp.types;

import com.intellij.codeInsight.daemon.impl.quickfix.AddMethodBodyFix;
import com.intellij.codeInsight.intention.BaseElementAtCaretIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public class MakeMethodDefaultIntention extends BaseElementAtCaretIntentionAction {

  private String text = "Make method default";

  @NotNull
  @Override
  public String getText() {
    return text;
  }

  @NotNull
  @Override
  public String getFamilyName() {
    return getText();
  }

  @Override
  public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
    final PsiMethod psiMethod = PsiTreeUtil.getParentOfType(element, PsiMethod.class, false);
      if (psiMethod != null && PsiUtil.isLanguageLevel8OrHigher(psiMethod)) {
        if (psiMethod.getBody() == null && !psiMethod.hasModifierProperty(PsiModifier.DEFAULT)) {
          final PsiClass containingClass = psiMethod.getContainingClass();
          if (containingClass != null && containingClass.isInterface()) {
            text = "Make '" + psiMethod.getName() + "()' default";
            return true;
          }
        }
      }
      return false;
  }

  @Override
  public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
    final PsiMethod psiMethod = PsiTreeUtil.getParentOfType(element, PsiMethod.class, false);
    if (psiMethod != null) {
      final PsiModifierList modifierList = psiMethod.getModifierList();
      modifierList.setModifierProperty(PsiModifier.DEFAULT, true);
      new AddMethodBodyFix(psiMethod).invoke(project, editor, psiMethod.getContainingFile());
    }
  }
}
