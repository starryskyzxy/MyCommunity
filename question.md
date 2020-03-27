问题：
1.user数据有的字段无法自动封装到对象
解决：mybatis的驼峰命名映射还是要开启的，实体类的名字和数据库表的字段名一定要匹配

2.input框里无法显示取出的值，值显示再input框下面了
解决：用thymeleaf模板的时候，input里的值要用th:value
ps:<textarea>要用th:text

3.每张数据库表都要增加两个字段：数据创建时间，数据修改时间

4.当要使用到多个实体类时，可以考虑增加一个实体类，将需要用到的实体类组合进去，同时添加
service层，当要完成的业务无法用单表单语句完成时，要添加service层

5.fastjson可以自动进行驼峰映射

6.当question数据库中没有数据时，totalpage（总页数会为0），此时page>totalpage会出现问题，
因为page的默认值为1，会大于totalpage，我的办法是，如果查询数量为0，那直接让分页查询从0开始

7.查询数据库时一定要注意查询值为空的情况

8.当mapper查询的返回值为对象时，mapper.xml中必须要有结果类型，包装类型也需要

9.开了二级缓存就算修改了数据也会复用上一次的查询结果

10.如果没有在mapper接口上添加@mapper注解，为了将接口交由spring管理，必须另外在配置文件中
声明或者在springboot启动类上添加@MapperScan("com.zxy.mapper")注解
ps:加了@MapperScan还是不起作用，还要在mapper文件上添加@Repository

11.使用mybatis-generator时要在jdbcConnection标签下加入<property name="nullCatalogMeansCurrent" value="true"/>
防止生成的实体类和数据库不匹配

12.当出现.StackOverflowError异常时，debug一下，看看是否方法循环调用了

13.浏览器会缓存css文件和js文件，如果没有版本号就要用ctrl+f5手动刷新