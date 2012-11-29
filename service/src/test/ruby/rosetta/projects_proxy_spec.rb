require 'spec_helper'
require 'java'
require 'rosetta/handler'

ProjectsProxy = Jython::Class.import "ProjectsProxy", :from => "rosetta.projects_proxy"

describe "ProjectsProxy" do
  it "should get project data for rails" do
    object_mapper = Java::OrgCodehausJacksonMap::ObjectMapper.new
    projects_proxy = ProjectsProxy.new(object_mapper)

    expected_project = Java::RosettaService::Project.new(nil, "rails", "rails", [
        Java::RosettaService::Language.new("Ruby", 7585665),
        Java::RosettaService::Language.new("JavaScript", 78163)
    ])

    handler = Class.new do
      include Java::RosettaService::LookupHandler

      def found project
        project
      end
    end

    projects_proxy.find("rails", "rails", handler.new).should == expected_project
  end
end