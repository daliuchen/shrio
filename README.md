## jwt
在这个例子中涉及的表有
- sys_auth 权限表
- sys_role      角色
- sys_role_auth 角色权限表
- sys_user     用户表
- sys_user_role 用户角色关联表

用户  角色  权限 关系都是多对多的关系


JWT 
     jwt就是一个规范吧。将用户的信息放在请求头的里面，用户初次登录的时候，查询用户的信息。通过jwt的包搞成一个 token字符串给前端，前端每次请求来都是 在请求头里面放 Authorization（key）  Bearer token字符串(token)
    注意有空格。这种特别适合分布式系统也能实现单点登录。因为分布式最大的问题在共享session，可以通过redis做到。
      请求每次来，通过网关的时候就能解析。或者自己在方法里面也能解析。或者通过拦截器解析。
      
## 应该就是这样了，大体来说就是这个样子。
思路:
    登录
        先去user表中查
        在去sys_user_role 中查roleid 通过roleId去sys_role_auth表中查，通过authId再去sys_auth中查。就能的到用户的权限了。
        重点是对于sys_auth 的数据处理。我用parentId 为0表示是顶级节点。type为0页面路由，1为权限路由。在构建实体类。组装遍历
         ```java
         组装的时候主要是通过parenid来的。
            先分类，在找root下面的所有的子节点，在找子节点下面的所有的权限，完了之后，在找root下面的所有的权限。
         ```
    拦截器
        springmv继承 HandlerInterceptorAdapter  重写prehandle方法
    
         public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                /**
                 * - 得到token 解析
                 * - 得到这次请求的request中的url
                 * - 看token里面有没有，这里就比较灵活了，在登录之后，可以将这些的登录信息，放在redis中，在这里就可以通过redis操作了。
                 * 注意：
                 *   这里的权限配置重点在于
                 *      token里面的权限是从数据库查的
                 *      但是怎么获得url呢
                 *         两种方法：
                 *          - 传统的request获得servletPath 获得url请求看里面有没有。
                 *          - 通过requestMapping的name属性。将 object 的handle变为 HandleMethod对象 获得 RequestMapping 的 annotaion对象获得name属性，然后判断看有没有
                 *
                 */
                /**
                 * 方式一
                 */
                String requestURI = request.getRequestURI();
                //下面就看token里面有没有了
        
                /**
                 * 方式二
                 */
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                RequestMapping methodAnnotation = handlerMethod.getMethodAnnotation(RequestMapping.class);//这样就有一个弊端 就会限制后端 ，只能写RequestMapping 注解，别的就不能写了。如果是post的话，就只能在type属性里面写了
                String name = methodAnnotation.name();
                //看 auth的name包含不包含这个，只需要在auth表中需要添加一个 独一无二的每一个url的请求。
        
        
                //如果有就放行。
                //没有就报错，全局处理异常。ok
            }
        ```
        
   然后在配置一下那些url需要拦截就好了
   ```java
@Configuration
public class SystemConfig extends WebMvcConfigurationSupport {
    @Autowired
    private JwtInterceptor jwtInterceptor;
@Override
public void addInterceptors(InterceptorRegistry registry) { registry.addInterceptor(jwtInterceptor).
addPathPatterns("/**"). excludePathPatterns("/frame/login","/frame/register/**"); //设置不拦截的请求地址
} }
```
        