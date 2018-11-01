#include "xmlnode.h"

XmlNode::XmlNode(std::string &name)
{
    this->name = name;
}

std::string XmlNode::getName()
{
    return name;
}

std::string XmlNode::getData()
{
    return data;
}

std::string XmlNode::getAttr(std::string &attrib)
{
    if (attr != nullptr) {
        std::map::const_iterator pos = this->attr.find(attrib);

        if (pos == attr.end()) {
            //raise error
        }
        else {
            return pos->second;
        }
    }
    else {
        return nullptr;
    }
}

XmlNode XmlNode::getChild(std::string &childName)
{
    if (this->childNodes != nullptr) {
         std::map::const_iterator pos = this->childNodes.find(childName);

         if (pos == childNodes.end()) {
             //raise error
         }
         else {
             std::list <XmlNode> nodes =  pos->second;
             if (nodes != nullptr && !nodes.empty()) {
                 return nodes.front();
             }

             return nullptr;
         }
    }
}

XmlNode XmlNode::getChildWithAttr(std::string &childName, std::string &attrib, std::string &value)
{
    std::list <XmlNode> children = getChildren(childName);

    if (children == nullptr  || children.empty()) {
        return nullptr;
    }

    for (XmlNode ch: children) {
        std::string v = ch.getAttr(attrib);

        if (value == v) {
            return ch;
        }
    }

    return nullptr;
}

std::list<XmlNode> XmlNode::getChildren(std::string &nameCh)
{
    if (this->childNodes != nullptr) {
         std::map::const_iterator pos = this->childNodes.find(nameCh);

         if (pos == childNodes.end()) {
             //raise error
         }
         else {
             std::list <XmlNode> children =  pos->second;
             if (children != nullptr) {
                 return children;
             }
         }
    }

    return std::list <XmlNode>();
}

void XmlNode::addAttr(std::string &attrib, std::string &value)
{
    if (this->attr == nullptr) {
        this->attr = std::map<std::string, std::string>();
    }

    this->attr.insert(std::pair <std::string, std::string>(attrib, value));
}

void XmlNode::addChild(XmlNode &child)
{
    if (childNodes == nullptr) {
        childNodes = std::map <std::string, std::list <XmlNode>>();
    }

     std::map::const_iterator pos = this->childNodes.find(child.name);

     if (pos == childNodes.end()) {
         //raise error
     }
     else {
         std::list <XmlNode> list =  pos->second;
         if (list == nullptr) {

         }
     }

}




