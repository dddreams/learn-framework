package com.shure.business.demo.controller;

import com.shure.business.demo.service.StudentService;
import com.shure.business.protocol.input.StudentInput;
import com.shure.common.core.result.Codes;
import com.shure.common.core.result.R;
import com.shure.common.core.utils.RUtils;
import com.shure.common.web.apiversion.annotation.ApiVersion;
import com.shure.common.web.excrption.ServiceException;
import com.shure.data.entity.demo.TestStudent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Shure
 * @date 2022/1/20 11:04
 */
@Slf4j
@RestController
public class DemoController {

    @Value("${com.name}")
    private String comName;

    @Autowired
    private StudentService studentService;

    @PostMapping("/insertStu")
    public R insertStudent(@Valid StudentInput input) {
        System.out.println("学生信息：" + input);

        TestStudent stu = new TestStudent();
        BeanUtils.copyProperties(input, stu);
        boolean flag = studentService.save(stu);

        log.info("保存学生记录！");

        return RUtils.createSucc(input);
    }


    @GetMapping("/test")
    public R<String> test() {
        log.info("[test-controlle]----> 测试日志");
        String user = null;
        Assert.notNull(user, "用户信息不能为空");

        if (user == null) {
            throw new ServiceException(Codes.SERVER_EXCEPTION.code, "用户不能为空");
        }

        return RUtils.createSucc("测试 --->" + comName);
    }

    @GetMapping("/findAll")
    //@Paging
    public R findAll() {
        //PageUtils.setPage(1, 2);
        System.out.println("主线程-------" + Thread.currentThread().getName());
        return RUtils.createSucc(studentService.list());
    }

    @GetMapping("/find")
    @ApiVersion(1.0)
    public R find1() {
        log.info("日志，1.0版本");
        return RUtils.createSucc("1.0版本");
    }

    @GetMapping("/find")
    @ApiVersion(2.0)
    public R find2() {
        log.info("日志，2.0版本");
        return RUtils.createSucc("2.0版本");
    }
}
