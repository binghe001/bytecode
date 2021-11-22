/**
 * Copyright 2020-9999 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.binghe.bytecode.javassist.bean.factory.test;

import io.binghe.bytecode.javassist.bean.factory.UserDynamicFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author binghe (公众号：冰河技术)
 * @version 1.0.0
 * @description 测试生成的User类，并使用反射的方式调用User的方法
 */
public class UserDynamicFactoryTest {

    public static void main(String[] args) throws Exception {
        //获取User类的Class对象
        Class clazz = UserDynamicFactory.getUser();
        //调用无参构造方法
        Constructor constructor = clazz.getConstructor();
        Object obj = constructor.newInstance();
        Method method = clazz.getMethod("printName"  );
        method.invoke(obj, null);

        //调用有参构造方法
        constructor = clazz.getConstructor(String.class, int.class);
        //调用有参构造方法
        obj = constructor.newInstance("冰河", 18);
        method = clazz.getMethod("printName");
        method.invoke(obj, null);
    }
}
