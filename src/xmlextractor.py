#!/usr/bin/env python
# coding: utf-8

# In[24]:


from xml.dom import minidom
import os.path


# In[2]:


class Duplicate:
    def __init__(self, duplicate, projectDir):
        try:
            self.exp = int(duplicate.attributes['exp'].value)
        except:
            self.exp = 0
        fragmentsXml = list(filter(lambda node: node.nodeType != node.TEXT_NODE, duplicate.childNodes))
        self.fragments = [self.getFragmentText(fragment, projectDir) for fragment in fragmentsXml]
        
    def getFragmentText(self, fragment, projectDir):
        file = fragment.attributes['file'].value
        start = int(fragment.attributes['start'].value)
        end = int(fragment.attributes['end'].value)
        
        filePath = file.replace("file://$PROJECT_DIR$", projectDir)
        with open(filePath, "r") as f:
            text = f.read()
            return text[start : end]
            


# In[3]:


def parseXml(fileName, projectDir):
    # parse an xml file by name
    mydoc = minidom.parse(fileName)

    duplicates = mydoc.getElementsByTagName('duplicate')
    
    return [Duplicate(duplicate, projectDir) for duplicate in duplicates]

