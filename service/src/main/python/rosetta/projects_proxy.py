import urllib2
import sys

from rosetta.service import Projects
from rosetta.service import Language
from rosetta.service import Project

from java.util import HashMap

class ProjectsProxy(Projects):
    BASE_GITHUB_URL = "https://api.github.com/repos/"

    def __init__(self, json_mapper):
        self.json_mapper = json_mapper

    def find(self, user, repository, handler):
        try:
            f = urllib2.urlopen(self.BASE_GITHUB_URL + user + "/" + repository + "/languages")
        except urllib2.HTTPError:
            return handler.notFound()

        languages_node = self.json_mapper.readValue(f.read(), HashMap)

        languages = [Language(entry.key, entry.value) for entry in languages_node.entrySet()]

        return handler.found(Project(lambda x: self.json_mapper.writeValueAsString(x), user, repository, languages))
