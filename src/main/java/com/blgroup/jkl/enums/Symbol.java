package com.blgroup.jkl.enums;

/**
 * 
 *
 * @version 1.0
 * @author wangxiaobo
 * @createDate 2012-9-21
 * @modifyBy wangxiaobo
 * @modifyDate 2012-9-21
 */
public enum Symbol {
    /**
     * 反斜杠  “/”
     */
    Backslash("/"),
    /**
     * 点"."
     */
    Point("."),
    /**
     * ","
     */
    Comma(","),
    /**
     * ","
     */
    Equal("="),
    /**
     * "="
     */
    InDash("-"),
    /**
     * "_"
     */
    UnderDash("_"),
    /**
     * ":"
     */
    Colon(":"),
    
    /**
     * "#"
     */
    Sharp("#"),
    /**
     * ";"
     */
    Semicolon(";"),
    /**
     * "'"
     */
    Singlequotes("'");
    
    private String label;
    
    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    private Symbol(String _value) {
        this.label = _value;
    }

    /**
     * 比较
     * 
     * @param code
     * @return
     */
    public boolean compare(String label) {
        if(this.label.equals(label)){
            return true;
        }else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return this.label;
    }
}
