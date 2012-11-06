require 'clamp'

module Rosetta
  class Cli < Clamp::Command
    option '--port', '-p' 'PORT', 'server port', :default => 3000 do |s|
      Integer(s)
    end

    def execute
      Java::Rosetta::Application.boot self
    end
  end
end