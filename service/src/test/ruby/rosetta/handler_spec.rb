require 'rosetta/handler'

describe Rosetta::Handler do
  subject do
    Rosetta::Handler.new do |h|
      h.found { "FOUND" }

      h.not_found { "NOT FOUND" }
    end
  end

  its(:public_methods) { should include "found" }
  its(:public_methods) { should include "not_found" }

  context "#found" do
    it { subject.found.should == "FOUND" }
  end

  context "#not_found" do
    it { subject.not_found.should == "NOT FOUND" }
  end

  context "#undefined" do
    it { lambda { subject.undefined }.should raise_error(NoMethodError) }
  end

  describe "ClassMethods" do
    subject { Rosetta::Handler.new {} }

    it { lambda { subject.class.undefined }.should raise_error(NoMethodError) }
  end
end