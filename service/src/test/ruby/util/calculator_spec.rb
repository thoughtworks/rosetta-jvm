require 'spec_helper'

Calculator = Jython::Class.import "Calculator", :from => "util.calculator"

describe Calculator do
  let(:calculator) { Calculator.new }

  it "should instantiate python class" do
    calculator.should_not be_nil
  end

  it "should allow methods with no arguments to be called" do
    calculator.pi.should be_within(0.00001).of(3.14159)
  end

  it "should allow methods with primitive arguments to be called" do
    calculator.plus(1, 2).should == 3
  end

  it "should wrap exceptions and extract reason" do
    expect {
      calculator.throws_native_exception
    }.to raise_error(Jython::Exception, /AttributeError/)
  end

  xit "should expose python class instance methods when #public_methods called" do
    calculator.public_methods.should include("pi", "plus")
  end
end
