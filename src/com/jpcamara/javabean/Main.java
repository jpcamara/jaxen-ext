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
    beany.nestedArray.put("accessToArray", new Integer[]{ 123123, 45435, 34983 });
    
    EnhancedJavaBeanXPath path = new EnhancedJavaBeanXPath(".");
    Element root = (Element)path.selectSingleNode(beany);
    
    EnhancedJavaBeanXPath primitiveBool = new EnhancedJavaBeanXPath("great");
    EnhancedJavaBeanXPath boxedBool = new EnhancedJavaBeanXPath("sweet");
    EnhancedJavaBeanXPath hasBool = new EnhancedJavaBeanXPath("awesomeness");
    EnhancedJavaBeanXPath mapValue = new EnhancedJavaBeanXPath("mappy/key1");
    EnhancedJavaBeanXPath listValue = new EnhancedJavaBeanXPath("otherMappy/listKey[1]");
    EnhancedJavaBeanXPath nested = new EnhancedJavaBeanXPath("nestedMappy/nestedKey[2]/key2[2]");
    EnhancedJavaBeanXPath arrayAccess = new EnhancedJavaBeanXPath("stringsArray");
    EnhancedJavaBeanXPath nestedArrayAccess = new EnhancedJavaBeanXPath("nestedArray/accessToArray[2]");
    
    System.out.println(root.getObject());
    
    long startTime = System.currentTimeMillis();
    System.out.println(((Element)primitiveBool.selectSingleNode(root)).getObject() );
    System.out.println(((Element)boxedBool.selectSingleNode(root)).getObject());
    System.out.println(((Element)hasBool.selectSingleNode(root)).getObject());
    System.out.println(((Element)mapValue.selectSingleNode(root)).getObject());
    System.out.println(((Element)listValue.selectSingleNode(root)).getObject());
    System.out.println(((Element)nested.selectSingleNode(root)).getObject());
    System.out.println("Arrays");
    System.out.println(((Element)arrayAccess.selectSingleNode(root)).getObject());
    System.out.println(((Element)nestedArrayAccess.selectSingleNode(root)).getObject());
    System.out.println("Time to run: " + (System.currentTimeMillis() - startTime));
    
    startTime = System.currentTimeMillis();
    System.out.println(((Element)primitiveBool.selectSingleNode(root)).getObject() );
    System.out.println(((Element)boxedBool.selectSingleNode(root)).getObject());
    System.out.println(((Element)hasBool.selectSingleNode(root)).getObject());
    System.out.println(((Element)mapValue.selectSingleNode(root)).getObject());
    System.out.println(((Element)listValue.selectSingleNode(root)).getObject());
    System.out.println(((Element)nested.selectSingleNode(root)).getObject());
    System.out.println(((Element)arrayAccess.selectSingleNode(root)).getObject());
    System.out.println(((Element)nestedArrayAccess.selectSingleNode(root)).getObject());
    System.out.println("Time to run: " + (System.currentTimeMillis() - startTime));
  }
  
  private static class Bean {
    private boolean great;
    private Boolean sweet;
    private Boolean awesomeness;
    private Map<String, String> mappy = new HashMap<String, String>();
    private Map<String, List<String>> otherMappy = new HashMap<String, List<String>>();
    private Map<String, List<HashMap<String, List<String>>>> nestedMappy = 
      new HashMap<String, List<HashMap<String, List<String>>>>();
    private String[] stringsArray = { "oh", "sure", "nice" };
    private HashMap<String, Integer[]> nestedArray = new HashMap<String, Integer[]>();
    
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
    
    public HashMap<String, Integer[]> getNestedArray() {
      return nestedArray;
    }
    
    public String[] stringsArray() {
      return stringsArray;
    }
  }
}