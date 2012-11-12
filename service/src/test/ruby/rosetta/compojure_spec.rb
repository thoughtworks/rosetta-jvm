require 'java'

describe 'Java::Rosetta::Compojure' do
  before(:all) do
    Java::ClojureLang::RT.var("clojure.core", "*compile-path*", "out/production/service/")
    Java::ClojureLang::RT.var("clojure.core", "compile").invoke(Java::ClojureLang::Symbol.create("rosetta.compojure"))
  end

  it "returns the result of 2 + 2" do
    Java::Rosetta::Compojure.new.invoke.should == 4
  end
end
