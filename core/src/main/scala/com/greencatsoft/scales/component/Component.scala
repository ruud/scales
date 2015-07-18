package com.greencatsoft.scales.component

import org.scalajs.dom.Element

import com.greencatsoft.scales.query.NodeProvider

trait Component[A <: Element] extends NodeProvider[Element] with LifecycleAware[A] {

  private var _element: Option[A] = None

  def element: A = _element getOrElse {
    throw new IllegalStateException("The component has not been initialized yet.")
  }

  override def contentRoot: Element = element

  override def onCreate(element: A): Unit = {
    super.onCreate(element)

    this._element = Some(element)
  }

  override def onDetach(element: A): Unit = {
    this._element = None

    super.onDetach(element)
  }
}