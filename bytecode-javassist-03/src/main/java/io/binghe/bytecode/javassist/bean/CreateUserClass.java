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
package io.binghe.bytecode.javassist.bean;

import javassist.*;

/**
 * @author binghe (公众号：冰河技术)
 * @version 1.0.0
 * @description 使用Javassist生成一个User类, 并测试
 */
public class CreateUserClass {

    /**
     * 使用Javassist创建一个User对象
     */
    public static void createUser() throws Exception{
        //使用默认的ClassPool
        ClassPool pool = ClassPool.getDefault();

        //1.创建一个空类
        CtClass ctClass = pool.makeClass("io.binghe.bytecode.javassist.bean.User");

        //2.新增一个字段 private String name; 字段的名称为name
        CtField param = new CtField(pool.get("java.lang.String"), "name", ctClass);
        //设置访问修饰符为private
        param.setModifiers(Modifier.PRIVATE);
        //设置字段的初始值为binghe
        ctClass.addField(param, CtField.Initializer.constant("binghe"));

        //3.添加无参的构造函数
        CtConstructor constructor = new CtConstructor(new CtClass[]{}, ctClass);
        constructor.setBody("{" +
                " $0.name = \"binghe\"; " +
                "}");
        ctClass.addConstructor(constructor);

        //4.添加有参构造函数
        constructor = new CtConstructor(new CtClass[]{pool.get("java.lang.String")}, ctClass);
        constructor.setBody("{" +
                "$0.name = $1;" +
                "}");
        ctClass.addConstructor(constructor);

        //5.添加getter和setter方法
        ctClass.addMethod(CtNewMethod.setter("setName", param));
        ctClass.addMethod(CtNewMethod.getter("getName", param));

        //6.创建一个输出name的方法
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "printName", new CtClass[]{}, ctClass);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("{" +
                "System.out.println(name);" +
                "}");
        ctClass.addMethod(ctMethod);

        ctClass.writeFile("D:\\Workspaces\\apm\\bytecode\\bytecode\\bytecode-javassist-03\\src\\main\\java");
    }
}
