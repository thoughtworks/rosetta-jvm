require 'jython/exception'

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
          begin
            python_args = args.map { |e| Java::OrgPythonCore::Py.java2py(e) }.to_java(Java::OrgPythonCore::PyObject)
            @instance.invoke(name.to_s, python_args).__tojava__ Java::JavaLang::Object.java_class
          rescue NativeException => exception
            raise Jython::Exception.new(exception.cause)
          end
        end
      end
    end
  end
end
