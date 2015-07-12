package com.greencatsoft.scales.component

import com.greencatsoft.greenlight.TestSuite

object MacroUtilsTest extends TestSuite {

  "MacroUtils.getAnnotatedValue[A, B]()" should "find an annotation B on type A and return its value" in {

    @name("cool-component")
    class CoolComponent

    val name = MacroUtils.getAnnotatedValue[CoolComponent, name]

    name should be (Some("cool-component"))
  }

  It should "return None when the specified type does not have the given annotation" in {

    class CoolComponent

    val name = MacroUtils.getAnnotatedValue[CoolComponent, name]

    name should be (empty)
  }

  It should "be able to handle multiple annotations" in {

    @name("cool-component")
    @inherit("parent-component")
    class CoolComponent

    val name = MacroUtils.getAnnotatedValue[CoolComponent, name]
    val parent = MacroUtils.getAnnotatedValue[CoolComponent, inherit]

    name should be (Some("cool-component"))
    parent should be (Some("parent-component"))
  }

  It should "be able to read the annotation present on a super class of the given type" in {

    @name("parent-component")
    trait ParentComponent

    class CoolComponent extends ParentComponent

    val name = MacroUtils.getAnnotatedValue[CoolComponent, name]

    name should be (Some("parent-component"))
  }

  It should "read the closest one when multiple annotations are present on the type hierarchy" in {

    @name("parent-component")
    trait ParentComponent

    @name("cool-component")
    class CoolComponent extends ParentComponent

    val name = MacroUtils.getAnnotatedValue[CoolComponent, name]

    name should be (Some("cool-component"))
  }
}