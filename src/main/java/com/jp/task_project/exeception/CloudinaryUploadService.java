package com.jp.task_project.exeception;

public class CloudinaryUploadService extends  RuntimeException{
    public CloudinaryUploadService (String massage){
        super(massage);
    }
}
