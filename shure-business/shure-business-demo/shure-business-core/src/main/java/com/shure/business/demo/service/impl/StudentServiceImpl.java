package com.shure.business.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shure.business.demo.service.StudentService;
import com.shure.data.entity.demo.TestStudent;
import com.shure.data.mapper.demo.dao.StudentDao;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Student)表服务实现类
 *
 * @author makejava
 * @since 2022-01-24 14:30:31
 */
@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentDao, TestStudent> implements StudentService {

    @Async
    @Override
    public List<TestStudent> list() {
        System.out.println("当前线程：" + Thread.currentThread().getName());
        return super.list();
    }

}

