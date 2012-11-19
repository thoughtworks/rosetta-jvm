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
            before(:each) { `unzip #{ENV["ARTIFACT"]} -d #{workdir}` }

            let(:workdir) { Dir.mktmpdir }

            instance_eval &block
          end
        end
      end

      def build_process *args
        ChildProcess.build(*([File.join(workdir, "/service/bin/service")] + args)).tap do |p|
          p.io.inherit! if ENV["DEBUG"]
          at_exit { p.stop }
        end
      end
    end
  end
end

