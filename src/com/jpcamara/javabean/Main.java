package com.jpcamara.javabean;

import java.util.Map;
import java.util.HashMap;

import com.jpcamara.javabean.EnhancedDocumentNavigator;
import com.jpcamara.javabean.EnhancedJavaBeanXPath;

import org.jaxen.javabean.Element;

public class Main {
  public static void main(String[] args) throws Exception {
    Bean beany = new Bean();
    beany.mappy.put("key1", "value1");
    beany.sweet = Boolean.TRUE;
    beany.awesomeness = Boolean.FALSE;
    
    EnhancedJavaBeanXPath path = new EnhancedJavaBeanXPath(".");
    Element root = (Element)path.selectSingleNode(beany);
    System.out.println(root.getObject());
    
    EnhancedJavaBeanXPath primitiveBool = new EnhancedJavaBeanXPath("great");
    EnhancedJavaBeanXPath boxedBool = new EnhancedJavaBeanXPath("sweet");
    EnhancedJavaBeanXPath hasBool = new EnhancedJavaBeanXPath("awesomeness");
    EnhancedJavaBeanXPath mapValue = new EnhancedJavaBeanXPath("mappy/key1");
    
    System.out.println(((Element)primitiveBool.selectSingleNode(root)).getObject());
    System.out.println(((Element)boxedBool.selectSingleNode(root)).getObject());
    System.out.println(((Element)hasBool.selectSingleNode(root)).getObject());
    System.out.println(((Element)mapValue.selectSingleNode(root)).getObject());
  }
  
  private static class Bean {
    private boolean great;
    private Boolean sweet;
    private Boolean awesomeness;
    private Map<String, String> mappy = new HashMap<String, String>();
    
    public boolean isGreat() {
      return great;
    }
    
    public Boolean isSweet() {
      return sweet;
    }
    
    public Boolean hasAwesomeness() {
      return awesomeness;
    }
    
    public Map<String, String> getMappy() {
      return mappy;
    }
  }
}