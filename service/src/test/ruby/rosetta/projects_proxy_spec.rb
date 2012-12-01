require 'spec_helper'
require 'java'
require 'rosetta/handler'

ProjectsProxy = Jython::Class.import "ProjectsProxy", :from => "rosetta.projects_proxy"

describe "ProjectsProxy" do
  it "should get project data for rails" do
    object_mapper = Java::OrgCodehausJacksonMap::ObjectMapper.new
    projects_proxy = ProjectsProxy.new(object_mapper)

    handler = Class.new do
      include Java::RosettaService::LookupHandler

      def found project
        project
      end
    end

    actual_project = projects_proxy.find("rails", "rails", handler.new)

    actual_project.user.should == "rails"
    actual_project.repository.should == "rails"
    actual_project.languages.collect(&:name).should == %w(Ruby JavaScript)
    actual_project.languages.all? { |l| l.should respond_to :weighting }
  end
end
