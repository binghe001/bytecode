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
package io.binghe.bytecode.javassist.bean.factory;

import javassist.*;

/**
 * @author binghe (公众号：冰河技术)
 * @version 1.0.0
 * @description 使用Javassist生成一个User类，并返回User类的Class对象
 */
public class UserDynamicFactory {

    /**
     * 生成并获取用户对象
     */
    public static Class getUser() throws Exception{
        //使用默认的Classpool
        ClassPool pool = ClassPool.getDefault();

        //1.创建一个空类
        CtClass ctClass = pool.makeClass("io.binghe.bytecode.javassist.bean.User");

        //2.新增一个字段 private String name; 字段名为name
        CtField nameParam = new CtField(pool.get(String.class.getName()), "name", ctClass);
        //设置name的访问修饰符为private
        nameParam.setModifiers(Modifier.PRIVATE);
        //设置字段的初始值为binghe
        ctClass.addField(nameParam, CtField.Initializer.constant("binghe"));

        //3.新增一个字段 private int age, 字段名称为age
        CtField ageParam = new CtField(CtClass.intType, "age", ctClass);
        //设置age的访问修饰符为private
        ageParam.setModifiers(Modifier.PRIVATE);
        //设置age的初始值为18
        ctClass.addField(ageParam, CtField.Initializer.constant(18));

        //4.添加无参构造方法
        CtConstructor constructor = new CtConstructor(new CtClass[]{}, ctClass);
        constructor.setBody("{" +
                "$0.name = \"binghe\";" +
                "$0.age = 18;" +
                "}");
        ctClass.addConstructor(constructor);

        //5.添加有参构造方法
        constructor = new CtConstructor(new CtClass[]{pool.get("java.lang.String"), CtClass.intType}, ctClass);
        constructor.setBody("{" +
                "$0.name = $1;" +
                "$0.age = $2;" +
                "}");
        ctClass.addConstructor(constructor);


        //6.添加setter和getter方法
        ctClass.addMethod(CtNewMethod.setter("setName", nameParam));
        ctClass.addMethod(CtNewMethod.getter("getName", nameParam));

        ctClass.addMethod(CtNewMethod.setter("setAge", ageParam));
        ctClass.addMethod(CtNewMethod.getter("getAge", ageParam));

        //7.创建一个打印name和age的方法
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "printName", new CtClass[]{}, ctClass);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("{" +
                "System.out.println(\"My name is \" + name + \", I am \" + age + \" years old.\" );" +
                "}");
        ctClass.addMethod(ctMethod);

        //将class文件输出到磁盘
        ctClass.writeFile("D:\\Workspaces\\apm\\bytecode\\bytecode\\bytecode-javassist-04\\src\\main\\java");


        return ctClass.toClass();
    }
}
