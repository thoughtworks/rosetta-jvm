require 'spec_helper'
require 'rhino'

describe "sprockets" do
  include Rosetta::Acceptance::DSL

  resource "assets" do
    it "should successfully compile coffeescript" do

      process = build_process "--port", "9999"
      process.start

      retry_count = 0
      begin
        response = Rosetta::Client.new("http://localhost:9999").get '/assets/calculator.js'
      rescue Errno::ECONNREFUSED => e
        sleep 1
        retry_count = retry_count + 1
        if retry_count > 10
          raise e
        else
          retry
        end
      end

      response.code.should == 200

      Rhino::Context.open do |context|
        context.eval(response.body)
        context.eval("square(3)").should == 9
      end
    end
  end
end