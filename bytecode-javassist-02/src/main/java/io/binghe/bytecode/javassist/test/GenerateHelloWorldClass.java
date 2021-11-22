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
package io.binghe.bytecode.javassist.test;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;

import java.lang.reflect.Method;

/**
 * @author binghe (公众号：冰河技术)
 * @version 1.0.0
 * @description 测试使用Javassist生成第一个类HelloWorld
 */
public class GenerateHelloWorldClass {

    /**
     * 创建HelloWorld的类，并返回HelloWorld的Class实例
     */
    public static Class createHelloWorld()throws Exception{
        //使用默认的ClassPool
        ClassPool pool = ClassPool.getDefault();
        //1.创建一个空类
        CtClass ctClass = pool.makeClass("io.binghe.bytecode.javassist.test.HelloWorld");
        //添加一个main方法
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "main", new CtClass[]{pool.get(String[].class.getName())}, ctClass);
        //将main方法声明为public static类型
        ctMethod.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
        //设置方法体
        ctMethod.setBody("{" +
                "System.out.println(\"Javassist Hello World by 冰河（公众号：冰河技术）\");" +
                "}");
        ctClass.addMethod(ctMethod);

        //将生成的类的class文件输出的磁盘
        ctClass.writeFile("D:\\Workspaces\\apm\\bytecode\\bytecode\\bytecode-javassist-02\\src\\main\\java");

        //返回HelloWorld的Class实例
        return ctClass.toClass();

    }

    public static void main(String[] args) throws Exception {
        Class clazz = createHelloWorld();
        Object obj = clazz.newInstance();
        Method mainMethod = clazz.getMethod("main", new Class[]{String[].class});
        mainMethod.invoke(obj, new String[1]);
    }
}
