require 'httparty'

module Rosetta
  class Client
    def self.new base_uri
      ::Class.new do
        include HTTParty

        base_uri base_uri
      end
    end
  end
end

module Rosetta
  module Acceptance
    module DSL
      def self.included base
        base.send(:extend, ClassMethods)
      end

      module ClassMethods
        def resource name, &block
          context name do
            before(:each) { `unzip #{ENV["ARTIFACT"]} -d #{workdir}`; process.start }
            after(:each) { process.stop; process.poll_for_exit(10) }

            let(:workdir) { Dir.mktmpdir }

            let(:process) do
              process = build_process "--port", "9999"
              process
            end

            let(:client) do
              retry_count = 0
              result = nil
              begin
                result = Rosetta::Client.new("http://localhost:9999")
                result.get("/ping")
              rescue Errno::ECONNREFUSED => e
                sleep 1
                retry_count = retry_count + 1
                if retry_count > 10
                  raise e
                else
                  retry
                end
              end
              result
            end

            instance_eval &block
          end
        end
      end

      def build_process *args
        ChildProcess.build(*([File.join(workdir, "/service/bin/service")] + args)).tap do |p|
          p.io.inherit! if ENV["DEBUG"]
        end
      end
    end
  end
end

