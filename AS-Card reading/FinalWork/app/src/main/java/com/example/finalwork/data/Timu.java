package com.example.finalwork.data;

public class Timu {
    private int ImageId;
    private String Question;
    private String[] Answer;
    public Timu(int ImageId,String Question,String[] Answer){
        this.ImageId=ImageId;
        this.Question=Question;
        this.Answer=Answer;
    }
    public int getImageId(){
        return ImageId;
    }
    public String getQuestion(){return Question;}
    public String[] getAnswer(){return Answer;}
}
