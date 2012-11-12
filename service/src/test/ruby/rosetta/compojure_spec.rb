require 'java'

describe 'Java::Rosetta::Compojure' do
  it "returns the result of 2 + 2" do
    Java::Rosetta::Compojure.new.invoke.should == 4
  end
end
