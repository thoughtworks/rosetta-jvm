require 'spec_helper'
require 'childprocess'
require 'tmpdir'

describe 'application jar' do
  include Rosetta::Acceptance::DSL

  resource "ping" do
    xit "should have a help menu" do
      process = build_process "--help"
      process.start
      process.poll_for_exit(10)

      process.exit_code.should == 0
    end

    xit "should have a ping resource" do
      client.get('/ping').code.should == 200
    end
  end
end
