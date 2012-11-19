require 'clamp'

module Rosetta
  class Cli < Clamp::Command
    def self.classpath_resource name
      Java::JavaLang::Thread.current_thread.context_class_loader.get_resource(name).path
    end

    option '--port', '-p' 'PORT', 'server port', :default => 3000 do |s|
      Integer(s)
    end

    option '--assets', '-a' 'ASSETS', 'asset root of application',
           :default => File.join(classpath_resource("assets"), "")

    def execute
      Java::RosettaBoot::Application.boot self
    end
  end
end