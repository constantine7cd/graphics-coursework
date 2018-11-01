#ifndef XMLNODE_H
#define XMLNODE_H

#include <list>
#include <string>
#include <map>

class XmlNode
{
public:
    XmlNode() = default;

    XmlNode(std::string &name);
    std::string getName();
    std::string getData();

    //По имени атрибута получаем его значение
    std::string getAttr(std::string &attr);

    XmlNode getChild(std::string &childName);
    XmlNode getChildWithAttr(std::string &childName, std::string &attr, std::string &value);

    std::list <XmlNode> getChildren(std::string &name);
    void addAttr(std::string &attr, std::string &value);
    void addChild(XmlNode &child);

    setData(std::string &data);



private:
    std::string name;
    std::map <std::string, std::string> attr;
    std::string data;
    std::map <std::string, std::list <XmlNode>> childNodes;
};

#endif // XMLNODE_H
