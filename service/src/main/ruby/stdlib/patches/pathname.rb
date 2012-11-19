require 'pathname'

class Pathname
  def absolute?
    to_s =~ /^file:/ || !relative?
  end
end

