module Jython
  class Class
    def self.import clazz, opts
      interpreter = Java::OrgPythonUtil::PythonInterpreter.new
      interpreter.exec("from #{opts[:from]} import #{clazz}")
      python_class = interpreter.get(clazz)

      ::Class.new do
        attr_reader :instance

        define_method :initialize do |*args|
          python_arguments = args.map do |x|
            Java::OrgPythonCore::PyString.new(x)
          end
          @instance = python_class.__call__ *python_arguments
        end

        define_method :method_missing do |name, *args|
          @instance.invoke(name.to_s).__tojava__ Java::JavaLang::Object.java_class
        end
      end
    end
  end
end
