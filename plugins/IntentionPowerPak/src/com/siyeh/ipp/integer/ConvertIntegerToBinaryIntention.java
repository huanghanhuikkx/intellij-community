/*
 * Copyright 2000-2011 JetBrains s.r.o.
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
package com.siyeh.ipp.integer;

import com.intellij.psi.PsiType;
import com.siyeh.ipp.base.PsiElementPredicate;
import org.jetbrains.annotations.NotNull;

public class ConvertIntegerToBinaryIntention extends ConvertNumberIntentionBase {
  @Override
  @NotNull
  public PsiElementPredicate getElementPredicate() {
    return new ConvertIntegerToBinaryPredicate();
  }

  @Override
  protected String convertValue(final Number value, final PsiType type, final boolean negated) {
    if (PsiType.INT.equals(type)) {
      final int intValue = negated ? -value.intValue() : value.intValue();
      return "0b" + Integer.toBinaryString(intValue);
    }
    else if (PsiType.LONG.equals(type)) {
      final long longValue = negated ? -value.longValue() : value.longValue();
      return "0b" + Long.toBinaryString(longValue) + "L";
    }

    return null;
  }
}
