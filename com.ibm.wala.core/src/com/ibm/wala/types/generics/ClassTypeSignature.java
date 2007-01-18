/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.wala.types.generics;

import com.ibm.wala.types.TypeName;

/**
 * Under construction.
 * 
 * ClassTypeSignature:
 *   L PackageSpecifier* SimpleClassTypeSignature ClassTypeSignatureSuffix* ;
 *   
 * SimpleClassTypeSignature:
 *   Identifier TypeArguments?
 *   
 * TypeArguments:
 *   <TypeArguments+>
 * 
 * @author sjfink
 * 
 */
public class ClassTypeSignature extends TypeSignature {

  ClassTypeSignature(String s) {
    super(s);
    assert (s.charAt(0) == 'L');
    assert (s.charAt(s.length()-1) == ';');
  }

  public static ClassTypeSignature makeClassTypeSig(String s) {
    return new ClassTypeSignature(s);
  }

  @Override
  public boolean isTypeVariable() {
    return false;
  }

  @Override
  public boolean isClassTypeSignature() {
    return true;
  }

  public TypeName getRawName() {
    String s = rawString().substring(0,rawString().length()-1);
    s = s.replaceAll("<.*>","");
    return TypeName.string2TypeName(s);
  }

  public TypeArgument[] getTypeArguments() {
    if (rawString().indexOf('<') == -1) {
      return null;
    } else {
      int start = rawString().indexOf('<');
      int end = endOfTypeArguments();
      return TypeArgument.make(rawString().substring(start,end));
    }
  }
  
  private int endOfTypeArguments() {
    int i = rawString().indexOf('<') + 1;
    assert (i > 0);
    int depth = 1;
    while (depth > 0) {
      if (rawString().charAt(i) == '>') {
        depth--;
      }
      if (rawString().charAt(i) == '<') {
        depth++;
      }
      i++;
    }
    return i;
  }

}
