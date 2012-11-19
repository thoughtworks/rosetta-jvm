require 'spec_helper'
require 'childprocess'
require 'tmpdir'

describe 'application jar' do
  include Rosetta::Acceptance::DSL

  resource "ping" do
    it "should have a help menu" do
      process = build_process "--help"
      process.start
      process.poll_for_exit(10)

      process.exit_code.should == 0
    end

    it "should have a ping resource" do
      process = build_process "--port", "9999"
      process.start

      retry_count = 0
      begin
        response = Rosetta::Client.new("http://localhost:9999").get '/ping'
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
    end
  end
end
