package com.jp.task_project.exeception;

public class ImageNotFoundException extends  RuntimeException{

    public ImageNotFoundException(String massage){
        super(massage);
    }
}
