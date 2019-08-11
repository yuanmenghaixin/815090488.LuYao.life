package com.luyao.community.controller;

import com.luyao.community.dto.CommentDTO;
import com.luyao.community.dto.QuestionDTO;
import com.luyao.community.enums.CommentTypeEnum;
import com.luyao.community.service.CommentService;
import com.luyao.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 返回问题界面
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        //根据id查找问题
        QuestionDTO questionDTO = questionService.getById(id);
        //查询相关问题展示
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        //回复列表
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }

}
