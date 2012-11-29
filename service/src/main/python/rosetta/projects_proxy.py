import urllib2
import sys

class ProjectsProxy:
    BASE_GITHUB_URL = "https://api.github.com/repos/"

    def __init__(self, json_mapper):
        self.json_mapper = json_mapper

    def find_by_url(self, url):
        f = urllib2.urlopen(self.BASE_GITHUB_URL + url)
        projects_info = self.json_mapper.readTree(f.read())
        return Project(projects_info)

class Project:

    def __init__(self, jacksonNode):
        self.jacksonNode = jacksonNode

    def __getattr__(self, name):
        return lambda : self.jacksonNode.get(name).getTextValue()

