package com.gxuwz.subject.controller;

import com.gxuwz.subject.common.util.R;
import com.gxuwz.subject.mapper.ConclusionMapper;
import com.gxuwz.subject.service.IConclusionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

        int current = (page - 1) * limit;

        List<ConclusionMapper> list = service.findByName(name, current, limit);

        Integer total = service.getTotal(name);

        return R.ok().data("list", list).data("total", total);
    }

}
