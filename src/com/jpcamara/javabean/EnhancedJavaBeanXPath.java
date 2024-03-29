/*
 * Copyright 2000-2002 bob mcwhirter & James Strachan.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 * 
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 * 
 *   * Neither the name of the Jaxen Project nor the names of its
 *     contributors may be used to endorse or promote products derived 
 *     from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * ====================================================================
 * This software consists of voluntary contributions made by many 
 * individuals on behalf of the Jaxen Project and was originally 
 * created by bob mcwhirter <bob@werken.com> and 
 * James Strachan <jstrachan@apache.org>.  For more information on the 
 * Jaxen Project, please see <http://www.jaxen.org/>.
 */
package com.jpcamara.javabean;

import org.jaxen.Context;
import org.jaxen.BaseXPath;
import org.jaxen.JaxenException;
import org.jaxen.javabean.Element;
import org.jaxen.javabean.ElementIterator;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/** 
 * Enhancement of the JavaBeanXPath, that includes support for is/has boolean methods,
 * accessing entries from Map objects, and Array support. Had to include the full source and just change
 * the constructor since the original class didn't expose the full BaseXPath constructor.
 * @author JP Camara
 */
public class EnhancedJavaBeanXPath extends BaseXPath {
  public EnhancedJavaBeanXPath(String xpathExpr) throws JaxenException {
    super(xpathExpr, EnhancedDocumentNavigator.getInstance());
  }

  protected Context getContext(Object node) {
    if (node instanceof Context) {
      return (Context) node;
    }

    if (node instanceof Element) {
      return super.getContext(node);
    }

    if (node instanceof List) {
      List newList = new ArrayList();
      for ( Iterator listIter = ((List)node).iterator(); listIter.hasNext();) {
        newList.add(new Element(null, "root", listIter.next()));
      }

      return super.getContext(newList);
    }

    return super.getContext( new Element( null, "root", node ) );
  }

  public Object evaluate(Object node)
    throws JaxenException {
    Object result = super.evaluate(node);

    if (result instanceof Element) {
      return ((Element)result).getObject();
    }
    else if (result instanceof Collection) {
      List newList = new ArrayList();

      for (Iterator listIter = ((Collection)result).iterator(); listIter.hasNext();) {
        Object member = listIter.next();

        if (member instanceof Element) {
          newList.add(((Element)member).getObject());
        } else {
          newList.add( member );
        }
      }

      return newList;
    }

    return result;
  }
}