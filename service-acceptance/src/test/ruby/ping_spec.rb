require 'spec_helper'
require 'childprocess'
require 'httparty'
require 'tmpdir'

module Rosetta
  class Client
    def self.new
      ::Class.new do
        include HTTParty
      end
    end
  end
end

describe 'application jar' do
  before(:each) { `unzip #{ENV["ARTIFACT"]} -d #{workdir}` }

  let(:workdir) { Dir.mktmpdir }

  def build_process *args
    process = ChildProcess.build(*([File.join(workdir, "/service/bin/service")] + args))
    if ENV["DEBUG"]
      process.io.inherit!
    end

    at_exit do
      process.stop
    end

    process
  end

  it "should have a help menu" do
    process = build_process "--help"
    process.start
    process.poll_for_exit(10)

    process.exit_code.should == 0
  end

  xit "should have a ping resource" do
    process = build_process "--port", "3000"
    process.start

    retry_count = 0
    begin
      response = Rosetta::Client.new.get 'http://localhost:9999'
    rescue Errno::ECONNREFUSED => e
      sleep 1
      retry_count = retry_count + 1
      if retry_count > 10
        raise e
      else
        retry
      end
    end

    response.status.should == 200
  end
end
