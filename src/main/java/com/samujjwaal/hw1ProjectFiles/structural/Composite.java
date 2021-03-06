package com.samujjwaal.hw1ProjectFiles.structural;

import com.samujjwaal.hw1ProjectFiles.DesignPattern;
import com.squareup.javapoet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.lang.model.element.Modifier;
import java.io.IOException;

public class Composite implements DesignPattern {
    //Define a static logger variable so that it references the Logger instance
    private static final Logger logger =  LoggerFactory.getLogger(Composite.class);


    public String[] defaultClasses = {"Component", "Composite"};
    public String packageName = "com.StructuralDP.composite";
    JavaFile[] generatedCode = new JavaFile[defaultClasses.length];

    public Composite(int flag)throws IOException{
        logger.info("Executing Composite()");
        if(flag == 1) {
            createDesignPattern(defaultClasses, packageName);
        }
    }

    @Override
    public String getDefaultPackageName() {
        return packageName;
    }

    @Override
    public String[] getDefaultClassName() {
        return defaultClasses;
    }

    public JavaFile[] generateCode(String[] classes, String packageName){

        logger.info("Executing generateCode()");
        int i = 0;

//        Component interface declaration
        ClassName Component = ClassName.get("",classes[i]);
        MethodSpec oper = MethodSpec.methodBuilder("operation")
                .addModifiers(Modifier.PUBLIC,Modifier.ABSTRACT)
                .returns(TypeName.VOID)
                .build();
        TypeSpec component = TypeSpec.interfaceBuilder(Component)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("Component declares the interface for objects in the composition")
                .addMethod(oper)
                .build();
        generatedCode[i] = JavaFile.builder(packageName,component)
                .skipJavaLangImports(true)
                .build();
        i += 1;

//        Composite class declaration
        ClassName Composite = ClassName.get("",classes[i]);
        ClassName list = ClassName.get("java.util", "List");
        ClassName arrayList = ClassName.get("java.util", "ArrayList");
        TypeName listOfComponents = ParameterizedTypeName.get(list,Component);

        FieldSpec child = FieldSpec.builder(listOfComponents,"children")
                .addModifiers(Modifier.PRIVATE)
                .initializer("new $T<$T>()",arrayList,Component)
                .build();
        TypeSpec composite =  TypeSpec.classBuilder(Composite)
                .superclass(Component)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("Composite class defines behavior for components having children, stores child\n" +
                            "components, implements child-related operations in the Component interface")
                .addField(child)
                .addMethod(MethodSpec.methodBuilder(oper.name)
                        .addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
                        .beginControlFlow("for($T component : $N)",Component,child.name)
                        .addStatement("component.$N()",oper.name)
                        .endControlFlow()
                        .build())
                .addMethod(MethodSpec.methodBuilder("add")
                        .addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
                        .addParameter(Component,"component")
                        .addStatement("$N.add(component)",child.name)
                        .build())
                .addMethod(MethodSpec.methodBuilder("remove")
                        .addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
                        .addParameter(Component,"component")
                        .addStatement("$N.remove(component)",child.name)
                        .build())
                .addMethod(MethodSpec.methodBuilder("getChild")
                        .addModifiers(Modifier.PUBLIC).returns(Component)
                        .addParameter(int.class,"index")
                        .addStatement("return $N.get(index)",child.name)
                        .build())
                .build();
        generatedCode[i] = JavaFile.builder(packageName,composite)
                .skipJavaLangImports(true)
                .build();

        logger.info("Returning generated java code to be written in files");

        return generatedCode;

    }
}
