module Jython
  class Exception < ::Exception
    attr_reader :cause

    def initialize cause
      super(cause.to_s)
      @cause = cause
    end

    def to_s
      @cause.to_string.strip
    end
  end
end
