package com.jp.task_project.exeception;

public class TaskNotFoundException extends  RuntimeException {

    public TaskNotFoundException (String massege){
        super(massege);
    }

}
