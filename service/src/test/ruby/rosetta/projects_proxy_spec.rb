require 'spec_helper'
require 'java'

ProjectsProxy = Jython::Class.import "ProjectsProxy", :from => "rosetta.projects_proxy"

describe "ProjectsProxy" do
  it "should get project data for rails" do
    object_mapper = Java::OrgCodehausJacksonMap::ObjectMapper.new
    projects_proxy = ProjectsProxy.new(object_mapper)

    rails_project_info = projects_proxy.find_by_url("rails/rails")

    begin
      full_name = rails_project_info.invoke("full_name")
    rescue NativeException => exception
      raise Jython::Exception.new(exception.cause)
    end

    full_name.to_string.should == "rails/rails"
  end
end