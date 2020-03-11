package com.zk.po.enums;

public enum  MainFileCategory {

    exam(1,"考试资料"),
    work(2,"作业课件"),
    it(3,"IT数码"),
    paper(4,"论文读物"),
    video(5,"影音视频"),
    gallery(6,"创意图库");

    private Integer id;
    private String name;




     MainFileCategory(Integer id,String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
