package com.qgstudio.anyworkc.data.model;

public class QuestionNew {
    private int questionId;  //问题id
    private int type;  //题目类型
    private String A;  //如果为选择题 则A的内容
    private String B;  //如果为选择题 则B的内容
    private String C;  //如果为选择题 则C的内容
    private String D;  //如果为选择题 则D的内容
    private String content;  //题目内容
    private double socre;  //该题分数
    private String other;  //如果为填空题，则为填空题的空数
    private String analysis;  //解析

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getSocre() {
        return socre;
    }

    public void setSocre(double socre) {
        this.socre = socre;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
}
