package com.verba.tools.xml;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.tools.display.StringTools;
import com.verba.tools.xml.parsing.XmlTag;
import com.verba.tools.xml.parsing.XmlTagType;
import com.verba.tools.xml.parsing.XmlText;

/**
 * Created by sircodesalot on 14/8/30.
 */
public class XmlElement extends XmlNode {
  private final String name;
  private final String content;
  private final QList<XmlNode> children;

  public XmlElement(String name, String content) {
    this.name = name;
    this.children = new QList<>();
    this.content = content;
  }

  public XmlElement(String name) {
    this.name = name;
    this.children = new QList<>();
    this.content = StringTools.emptyString();
  }

  public XmlElement(String name, XmlNode... children) {
    this(name, StringTools.emptyString(), children);
  }

  public XmlElement(String name, String content, XmlNode... children) {
    this.name = name;
    this.content = content;
    this.children = new QList<>(children);
  }

  public XmlElement(String name, String content, Iterable<XmlNode> elements) {
    this.name = name;
    this.content = content;
    this.children = new QList<XmlNode>(elements);
  }

  public boolean hasChildren() {
    return this.children.any();
  }

  public String name() {
    return this.name;
  }

  public String innerText() {
    return this.content;
  }

  public QIterable<XmlNode> children() {
    return this.children;
  }

  public XmlElement elementByName(String name) {
    return childrenByName(name).single();
  }

  public QIterable<XmlElement> childrenByName(String name) {
    return this.children
      .ofType(XmlElement.class)
      .where(child -> child.name.equals(name));
  }


  public String getContentIndented(int indent) {
    if (hasChildren()) {
      String start = StringTools.formatIndented(indent, "<%s>", this.name);
      String end = StringTools.formatIndented(indent, "</%s>", this.name);

      StringBuilder middle = new StringBuilder();

      if (content != null) {
        middle.append(content);
      }

      middle.append("\n");

      for (XmlNode node : this.children) {
        middle.append(node.getContentIndented(indent + 4));
        middle.append("\n");
      }

      return String.format("%s%s%s", start, middle, end);

    } else {
      if (!content.isEmpty()) {
        return StringTools.formatIndented(indent, "<%1$s>%2$s</%1$s>", this.name, this.content);

      } else {
        return StringTools.formatIndented(indent, "<%s />", this.name);

      }
    }
  }

  @Override
  public String toString() {
    return getContentIndented(0);
  }

  public static XmlElement parse(XmlLexer lexer) {
    XmlTag openingTag = XmlTag.parse(lexer);

    if (openingTag.type() == XmlTagType.SELF_CLOSED) {
      return new XmlElement(openingTag.name());
    }

    XmlText text = XmlText.parse(lexer);

    QList<XmlNode> innerElements = new QList<>();
    while (!lexer.isEof() && !(XmlTag.peekNextTag(lexer).name().equals(openingTag.name()))) {
      innerElements.add(XmlElement.parse(lexer));
    }

    XmlTag closingTag = XmlTag.parse(lexer);

    if (innerElements.any()) {
      return new XmlElement(openingTag.name(), text.toString(), innerElements);

    } else {
      return new XmlElement(openingTag.name(), text.toString());
    }
  }

  public static XmlElement parse(String text) {
    return parse(new XmlLexer(text));
  }

}
