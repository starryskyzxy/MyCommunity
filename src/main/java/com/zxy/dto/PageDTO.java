package com.zxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//封装分页所需的所有信息
public class PageDTO {

    private List<QuestionDTO> questionDTOList;

    private boolean showPrevious;

    private boolean showFirstPage;

    private boolean showNext;

    private boolean showEndPage;

    private Integer currentPage;

    private List<Integer> pages;

    private Integer totalPage;

    //封装显示分页列表所需信息
    public void setPageContent(int count, Integer page, Integer size) {
        if (count % size ==0){
            totalPage = count/size;
        }else{
            totalPage = count/size + 1;
        }
        this.currentPage=page;
        if (page<=0){
            this.currentPage = 1;
        }
        if (page>totalPage){
            this.currentPage=totalPage;
        }
        //计算要展示的页面列表
        this.pages = new ArrayList<Integer>();
        pages.add(currentPage);
        for (int i=1;i<=3;i++){
            if (currentPage-i>0){
                pages.add(0,currentPage-i);
            }
            if (currentPage+i<=totalPage){
                pages.add(currentPage+i);
            }
        }

        //判断是否展示跳转第一页标签,当页面标签列表中不包含第一页时展示
        if (pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }
        //判断是否展示跳转最后一页标签，当当前页面不包含最后一页时展示
        if (pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }
        //判断是否展示上一页的标签
        if (currentPage == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        //判断是否展示下一页的标签
        if (currentPage.equals(totalPage)){
            showNext = false;
        }else {
            showNext = true;
        }
    }

}
