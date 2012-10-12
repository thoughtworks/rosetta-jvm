require 'spec_helper'

describe "Projects repository." do
  it "should instantiate python class" do
    Calculator = Jython::Class.import "Calculator", :from => "util.calculator"

    calculator = Calculator.new

    calculator.plus(1, 2).should == 3
  end
end
