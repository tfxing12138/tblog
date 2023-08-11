# api接口文档

## 001、注册用户
接口地址：/user/register

请求方式：POST

请求参数：

```json
{
    "userName": "link", // 用户名
    "passWord": "Link1234", // 密码
    "email": "t18370240624@163.com" // 邮箱
}

```

返回结果：

```json
{
    "data": 1689525484559896577, // 用户id
    "code": 200,
    "message": "请求成功"
}
```

## 002、登录
接口地址：/user/login

请求方式：GET

请求参数：

|参数名称|参数类型|是否必填|备注|
|---|---|---|---|
|email|String|true|邮箱|
|passWord|String|true|密码|

返回结果：

```json
{
    "data": {
        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTY5MTY4NTU3MX0.H9flzZ2bdiJDk7PHgKaAmmvf3soo3uqwoMVtjJj1R2k"
    },
    "code": 200,
    "message": "请求成功"
}
```

## 03、用户列表
接口地址：/user/list

请求方式：GET

header：

|参数名称|参数类型|是否必填| 备注    |
|---|---|---|-------|
|token|String|true| token |

返回结果：

```json
{
    "data": [
        {
            "id": 1689525484559896577,
            "email": "t18370240624@163.com",
            "userName": "link",
            "passWord": "Link1234",
            "deleted": 0,
            "createTime": "2023-08-10T06:34:00.000+00:00",
            "updateTime": "2023-08-10T06:34:00.000+00:00"
        }
    ],
    "code": 200,
    "message": "请求成功"
}
```