require 'jython/class'

def pp_methods clazz
  pp (clazz.methods - Object.methods).sort
end
