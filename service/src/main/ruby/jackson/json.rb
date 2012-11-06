require 'java'

module Jackson
  class JSON
    def self.parse(string)
      @mapper ||= Java::org.codehaus.jackson.map.ObjectMapper.new
      @mapper.read_value string, Java::JavaUtil::HashMap.java_class
    end
  end
end

JSON = Jackson::JSON