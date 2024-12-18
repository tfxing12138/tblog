package com.link.tblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.link.tblog.entity.User;
import com.link.tblog.mapper.UserMapper;
import com.link.tblog.service.UserService;
import com.link.tblog.utils.UserUtils;
import com.link.tblog.entity.constant.NumberConstant;
import com.link.tblog.utils.AESUtil;
import com.link.tblog.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> list() {

        return userMapper.list();
    }

    @Override
    public Long register(User user) throws Exception {
        UserUtils.validUserName(user.getUserName());

        UserUtils.validPassWord(user.getPassWord());

        UserUtils.validEmail(user.getEmail());

        validUniqueEmail(user.getEmail());

        UserUtils.saltHandle(user);

        userMapper.insert(user);

        return user.getId();
    }

    /**
     * 校验邮箱是否存在
     * @param email
     */
    private void validUniqueEmail(String email) {
        List<User> userList = userMapper.selectList(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, email)
                .eq(User::getDeleted, NumberConstant.ZERO));

        if(CollectionUtils.isNotEmpty(userList)) {
            throw new RuntimeException("该账号已存在");
        }
    }

    /**
     * 检验用户名&密码
     * @param user
     */
    @Override
    public void validUser(User user) throws Exception {
        ValidUtils.validParam(user,"参数异常,无效的用户");

        User userDb = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, user.getEmail())
                .eq(User::getDeleted, NumberConstant.ZERO));

        ValidUtils.validParam(userDb, "登录失败");

        String decrypt = AESUtil.decrypt(userDb.getSalt(), userDb.getPassWord());

        if(!decrypt.equals(user.getPassWord())) {
            throw new RuntimeException("密码错误");
        }
    }

    /**
     * 删除用户
     * @param id
     */
    @Override
    public void remove(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(null);
    }
}
