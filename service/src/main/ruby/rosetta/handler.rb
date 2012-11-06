module Rosetta
  class Handler
    def self.new &block
      handler_class = ::Class.new do
        class << self
          def method_missing name, *args, &block
            define_method(name) do |*args|
              block.call *args
            end
          end
        end
      end

      block.call handler_class

      handler_class.new
    end
  end
end

