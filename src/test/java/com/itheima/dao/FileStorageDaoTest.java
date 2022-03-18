package com.itheima.dao;

import com.itheima.domain.FileStorage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileStorageDaoTest {
    @Autowired
    private FileStorageDao fileStorageDao;

    @Test
    void getById() {
        FileStorage fileStorage = new FileStorage();
        FileStorage fileStorage1 = fileStorageDao.selectById(1);
        System.out.println(fileStorage1);
    }

}
