require 'spec_helper'

describe "Projects repository." do
  it "should instantiate python class" do
    ProjectsRepository = Jython::Class.import "ProjectsRepository", :from => "github.projects_repository"

    projects_repository = ProjectsRepository.new("anything")

    projects_repository.should_not be_nil
    projects_repository.hello.should == "hello"
  end
end
