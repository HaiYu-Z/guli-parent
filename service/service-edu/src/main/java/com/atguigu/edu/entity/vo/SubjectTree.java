package com.atguigu.edu.entity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author HaiYu
 */
@Data
@NoArgsConstructor
public class SubjectTree implements Serializable {
    private String id;
    private String title;
    private List<SubjectTree> children = new ArrayList<>();

    public SubjectTree(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
