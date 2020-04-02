package com.gxuwz.subject.controller;

import com.gxuwz.subject.common.util.R;
import com.gxuwz.subject.service.IConclusionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tale
 * @since 2020-03-25
 */
@RestController
@RequestMapping("/conclusion")
public class ConclusionController {

    @Autowired
    private IConclusionService service;

    @GetMapping("/list")
    public R list(@RequestParam("name")String name,  @RequestParam("limit")Integer limit,
                  @RequestParam("page")Integer page){

        return R.ok();
    }

}
