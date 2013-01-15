require 'spec_helper'
require 'rhino'

describe "sprockets" do
  include Rosetta::Acceptance::DSL

  resource "assets" do
    xit "should successfully compile coffeescript" do
      response = client.get('/assets/ping.js')

      response.code.should == 200

      Rhino::Context.open do |context|
        context.eval(response.body)
        context.eval("ping()").should == "PONG"
      end
    end
  end
end