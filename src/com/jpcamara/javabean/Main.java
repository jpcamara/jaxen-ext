package com.jpcamara.javabean;

import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.Arrays;

import com.jpcamara.javabean.EnhancedDocumentNavigator;
import com.jpcamara.javabean.EnhancedJavaBeanXPath;

import org.jaxen.javabean.Element;

public class Main {
  public static void main(String[] args) throws Exception {
    Bean beany = new Bean();
    beany.mappy.put("key1", "value1");
    beany.otherMappy.put("listKey", Arrays.asList("123", "323"));
    beany.nestedMappy.put("nestedKey", Arrays.asList(
      new HashMap<String, List<String>>() {{
        put("key1", Arrays.asList("ok", "next"));
      }},
      new HashMap<String, List<String>>() {{
        put("key2", Arrays.asList("further", "more"));
      }}
    ));
    beany.sweet = Boolean.TRUE;
    beany.awesomeness = Boolean.FALSE;
    
    EnhancedJavaBeanXPath path = new EnhancedJavaBeanXPath(".");
    Element root = (Element)path.selectSingleNode(beany);
    
    EnhancedJavaBeanXPath primitiveBool = new EnhancedJavaBeanXPath("great");
    EnhancedJavaBeanXPath boxedBool = new EnhancedJavaBeanXPath("sweet");
    EnhancedJavaBeanXPath hasBool = new EnhancedJavaBeanXPath("awesomeness");
    EnhancedJavaBeanXPath mapValue = new EnhancedJavaBeanXPath("mappy/key1");
    EnhancedJavaBeanXPath listValue = new EnhancedJavaBeanXPath("otherMappy/listKey[1]");
    EnhancedJavaBeanXPath nested = new EnhancedJavaBeanXPath("nestedMappy/nestedKey[2]/key2[2]");
    
    System.out.println(root.getObject());
    System.out.println(((Element)primitiveBool.selectSingleNode(root)).getObject() );
    System.out.println(((Element)boxedBool.selectSingleNode(root)).getObject());
    System.out.println(((Element)hasBool.selectSingleNode(root)).getObject());
    System.out.println(((Element)mapValue.selectSingleNode(root)).getObject());
    System.out.println(((Element)listValue.selectSingleNode(root)).getObject());
    System.out.println(((Element)nested.selectSingleNode(root)).getObject());
  }
  
  private static class Bean {
    private boolean great;
    private Boolean sweet;
    private Boolean awesomeness;
    private Map<String, String> mappy = new HashMap<String, String>();
    private Map<String, List<String>> otherMappy = new HashMap<String, List<String>>();
    private Map<String, List<HashMap<String, List<String>>>> nestedMappy = 
      new HashMap<String, List<HashMap<String, List<String>>>>();
    
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
    
    public Map<String, List<String>> getOtherMappy() {
      return otherMappy;
    }
    
    public Map<String, List<HashMap<String, List<String>>>> getNestedMappy() {
      return nestedMappy;
    }
  }
}